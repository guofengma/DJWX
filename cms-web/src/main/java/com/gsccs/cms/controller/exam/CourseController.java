package com.gsccs.cms.controller.exam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gsccs.cms.auth.model.Unit;
import com.gsccs.cms.auth.service.ConfigService;
import com.gsccs.cms.auth.utils.AuthConst;
import com.gsccs.cms.bass.controller.CmsBaseController;
import com.gsccs.cms.bass.utils.Pager;
import com.gsccs.cms.core.service.SensitiveService;
import com.gsccs.cms.course.model.Course;
import com.gsccs.cms.course.model.CourseArticle;
import com.gsccs.cms.course.model.CourseFeed;
import com.gsccs.cms.course.service.CourseService;
import com.gsccs.cms.member.model.Member;
import com.gsccs.cms.member.model.PartyMember;
import com.gsccs.cms.member.model.PartyUnit;
import com.gsccs.cms.member.service.MemberService;
import com.gsccs.cms.member.service.PartyUserService;
import com.gsccs.cms.member.service.PartyUnitService;
import com.gsccs.cms.weixin.model.WxTemplet;
import com.gsccs.cms.weixin.service.WxAppService;
import com.gsccs.cms.weixin.service.WxTempletService;
import com.gsccs.cms.wx.service.MpMsgService;

/**
 * 课程管理
 * 
 * @author x.d zhang
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/course")
public class CourseController extends CmsBaseController {

	// 模板名称：党课提醒
	private String tmlShortId = "OPENTM407110104";
	// 模板消息字体颜色
	private static final String TEMPLATE_FRONT_COLOR = "#32CD32";

	@Resource
	private CourseService courseService;
	@Resource
	private SensitiveService sensitiveService;
	@Resource
	private MemberService memberService;
	@Resource
	private PartyUserService partyMemberService;
	@Resource
	private WxAppService wxAppService;
	@Resource
	private ConfigService configService;
	@Resource
	private MpMsgService mpMsgService;
	@Resource
	private WxTempletService wxTempletService;
	@Resource
	private PartyUnitService partyUnitService;

	/**
	 * 管理页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/list.do")
	public String courseList(Course param,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "addtime desc") String order,
			ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		if (!subject.hasRole(AuthConst.SYS_ADMIN)) {
			param.setWxappid(getWxApp().getId());
		}
		List<Course> courseList = courseService.find(param, order, currPage,
				pageSize);
		int totalCount = courseService.count(param);
		Pager pager = new Pager(request);
		pager.appendParam("id");
		pager.appendParam("title");
		pager.appendParam("pageSize", "" + pageSize);
		pager.appendParam("pageFuncId");
		pager.setCurrPage(currPage);
		pager.setPageSize(pageSize);
		pager.setTotalCount(totalCount);
		pager.setOutStrBootstrap("list.do");
		map.put("pageStr", pager.getOutStrBootstrap());
		map.put("list", courseList);
		map.put("order", order);
		return "course/course_list";
	}

	/**
	 * 新增页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/form.do")
	public String form(Integer id, ModelMap map) {
		if (null==getWxApp()) {
			map.put("msg", "无权限操作，请用公众号管理员帐号登录");
			map.put("isCloseWindow", true);
			map.put("isRefresh", true);
			return "admin/msg";
		}
		Course course = null;
		if (null != id) {
			course = courseService.getCourse(id);
		}

		List<PartyUnit> unitList = partyUnitService.findByCorpid(getWxApp().getId());
		map.put("course", course);
		map.put("unitList", unitList);
		return "course/course_form";
	}
	
	
	/**
	 * 新增页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/articleAdd.do")
	public String articleAdd(Integer id, ModelMap map) {
		Course course = null;
		if (null != id) {
			course = courseService.getCourse(id);
		}
		map.put("course", course);
		return "course/article_form";
	}
	
	@RequestMapping("/articleAddDo.do")
	public String articleAdd(CourseArticle courseArticle, ModelMap map) {
		if (null==courseArticle 
				|| null == courseArticle.getArticleid() 
				|| null == courseArticle.getCourseid()) {
			map.put("msg", "保存失败,信息有误.");
			map.put("isCloseWindow", true);
			map.put("isRefresh", true);
			return "admin/msg";
		}
		
		try{
			courseService.addArticle(courseArticle);
			map.put("msg", "保存成功");
		}catch(Exception e){
			map.put("msg", "保存失败,错误原因:"+e.getMessage());
		}
		map.put("isCloseWindow", true);
		map.put("isRefresh", true);
		return "admin/msg";
	}

	
	/**
	 * 删除
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/articleDelDo.do")
	public String articleDelDo(Integer id,ModelMap map) {
		
		CourseArticle courseArticle = courseService.getArticle(id);
		if (null != courseArticle) {
			courseService.delArticle(id);
			map.put("msg", "操作成功");
			map.put("forwardUrl", "articleList.do?id=" + courseArticle.getCourseid());
		}else{
			map.put("forwardUrl", "list.do");
			map.put("msg", "操作失败，信息不存在。");
		}
		map.put("forwardSeconds", 3);
		return "admin/msg";
	}
	/**
	 * 保存
	 * 
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/save.do")
	public String saveCourse(Course course, ModelMap map) {
		String oper = "添加";
		if (null == course || getWxApp()==null) {
			map.put("msg", "课程保存失败");
			map.put("isCloseWindow", true);
			map.put("isRefresh", true);
			return "admin/msg";
		}
		try {
			Integer courseid = course.getId();
			// 添加course
			// course.setPlannum(Integer.valueOf(openid.length));
			if (null != courseid) {
				courseService.editCourse(course);
			} else {
				course.setWxappid(getWxApp().getId());
				courseid = courseService.addCourse(course);
			}

			if (null == courseid) {
				map.put("msg", "课程保存失败，请检查数据是否正确");
				map.put("isCloseWindow", true);
				map.put("isRefresh", true);
				return "admin/msg";
			}
			String url = getWxApp().getDomain() + "/web/course-" + courseid
					+ ".html";
			Map<String, String> data = new HashMap<String, String>();
			data.put("first", "你好，你有一条上课通知");
			data.put("keyword1", course.getName());
			data.put("keyword2", course.getAddtimestr());
			data.put("keyword3", course.getAddress());
			data.put("keyword4", "");
			data.put("keyword5", getWxApp().getTitle());
			data.put("remark", "请认真学习!");

			List<Member> memberList = null;
			// 课程公开
			if (course.getIsopen().equals("1")) {
				Member member = new Member();
				member.setWxappid(getWxApp().getId());
				memberList = memberService.find(member, "");
			} else {
				// 查询党员
				memberList = memberService.findAuthedList(getWxApp().getId(),"");
			}
			
			Map<String, Map<String, String>> userMsgMap = new HashMap<>();
			if (null != memberList && memberList.size() > 0) {
				for (Member m : memberList) {
					userMsgMap.put(m.getId(), data);
				}
				//userMsgMap.put("ov-8jwDhx5evpuVFbPRp9lRmlZKg", data);
				//userMsgMap.put("ov-8jwOrsI0YD9dkL4rFRrLS_MYI", data);
				WxTemplet wxTemplet = wxTempletService.find(getWxApp().getId(), tmlShortId);
				if (null != wxTemplet){
					mpMsgService.sendBatchMsg(getWxApp().getId(), wxTemplet.getLongid(), url,
							userMsgMap);
				}
			}
			msg = oper + "课程(" + course.getName() + ")成功!";
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("msg", msg);
		map.put("isCloseWindow", true);
		map.put("isRefresh", true);
		return "admin/msg";
	}

	/**
	 * 删除
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/del.do")
	public String del(String pageFuncId, String ids,
			HttpServletRequest request, ModelMap map,
			HttpServletResponse response) {
		if (ids != null && ids.trim().length() > 0) {
			String[] idArr = ids.split(";");
			if (idArr != null && idArr.length > 0) {
				Course course = null;
				for (int i = 0; i < idArr.length; i++) {
					if (idArr[i].trim().length() > 0) {
						try {
							course = courseService.getCourse(Integer
									.parseInt(idArr[i]));
							if (course != null) {
								courseService.delCourseById(course.getId());
								msg = "删除课程(" + course.getName() + ")成功!";
							}
						} catch (Exception e) {
							e.printStackTrace();
							msg = "删除课程(" + course.getName() + ")失败:"
									+ e.toString() + "!";
						}
						operlogService.log(getLoginName(), msg, request);
					}
				}
			}
		}
		map.put("msg", "操作成功");
		map.put("forwardUrl", "course/list.do?pageFuncId=" + pageFuncId);
		map.put("forwardSeconds", 3);
		return "admin/msg";
	}

	/**
	 * 课程资料
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/articleList.do")
	public String infolist(Integer id, ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {
		List<CourseArticle> infoList = courseService.findInfoList(id);
		Course course = null;
		if (null != id) {
			course = courseService.getCourse(id);
		}
		map.put("course", course);
		map.put("list", infoList);
		return "course/article_list";
	}

	/**
	 * 课程反馈
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/feedList.do")
	public String feedList(Integer id, 
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "addtime desc") String order,
			ModelMap map) {
		List<CourseFeed> feedList = courseService.findFeedList(null, id);
		Pager pager = new Pager(request);
		pager.appendParam("id");
		pager.appendParam("title");
		pager.appendParam("pageSize", "" + pageSize);
		pager.appendParam("pageFuncId");
		pager.setCurrPage(currPage);
		pager.setPageSize(pageSize);
		pager.setTotalCount(10);
		pager.setOutStrBootstrap("feedList.do");
		map.put("pageStr", pager.getOutStrBootstrap());
		map.put("list", feedList);
		return "course/feed_list";
	}

	/**
	 * 课程人员
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/userList.do")
	public String userList(Integer id, ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {
		Course course = courseService.getCourse(id);
		List<PartyMember> userList = courseService.findByCourseid(id);
		map.put("list", userList);
		map.put("course", course);
		return "course/user_list";
	}

	/**
	 * 日期型数据转换，将页面上的表示日期限的字符串，转换为Date型
	 * **/
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));

	}

}
