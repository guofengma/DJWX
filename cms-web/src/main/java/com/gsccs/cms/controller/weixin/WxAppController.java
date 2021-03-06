package com.gsccs.cms.controller.weixin;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gsccs.cms.bass.controller.CmsBaseController;
import com.gsccs.cms.bass.utils.Pager;
import com.gsccs.cms.core.model.Site;
import com.gsccs.cms.core.service.SiteService;
import com.gsccs.cms.weixin.model.WxApp;
import com.gsccs.cms.weixin.service.WxAppService;

/**
 * 微信账号管理
 * 
 * @author x.d zhang
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/wxapp")
public class WxAppController extends CmsBaseController {

	@Resource
	private WxAppService wxAppService;
	@Resource
	private SiteService siteService;
	/**
	 * 管理页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/list.do")
	public String list(WxApp param,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "") String order, ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {

		List<WxApp> wxAppList = wxAppService.find(param, order, currPage,
				pageSize, false);
		int totalCount = wxAppService.count(param);
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
		map.put("list", wxAppList);
		map.put("order", order);
		return "weixin/app_list";
	}

	
	/**
	 * 新增表单页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/add.do")
	public String wxAppEdit(ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {
		return "weixin/app_add";
	}

	
	/**
	 * 修改表单页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/edit.do")
	public String wxAppEdit(String id, ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {
		WxApp wxApp = wxAppService.findById(id);
		map.put("wxapp", wxApp);
		return "weixin/app_edit";
	}

	
	/**
	 * 编辑
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/editDo.do")
	public String editDo(WxApp wxApp, ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		String oper = "修改";
		if (null==wxApp || StringUtils.isEmpty(wxApp.getId())){
			map.put("msg", "保存失败，数据有误!");
			map.put("isCloseWindow", true);
			map.put("isRefresh", true);
			return "admin/msg";
		}
		try {
			wxAppService.update(wxApp);
			Site site = siteService.findById(wxApp.getId());
			if (null==site){
				site = new Site();
				site.setId(wxApp.getId());
				site.setName(wxApp.getTitle());
				siteService.insert(site);
			}
			msg = oper + "公众号(" + wxApp.getTitle() + ")成功!";
		} catch (Exception e) {
			e.printStackTrace();
			msg = oper + "公众号(" + wxApp.getTitle() + ")失败:" + e.toString()
					+ "!";
		}
		operlogService.log(getLoginName(), msg, request);
		map.put("msg", msg);
		map.put("isCloseWindow", true);
		map.put("isRefresh", true);
		return "admin/msg";
	}
	
	
	/**
	 * 编辑
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("/addDo.do")
	public String addDo(WxApp wxApp, ModelMap map, HttpServletRequest request,
			HttpServletResponse response) {
		String oper = "添加";
		if (null==wxApp || StringUtils.isEmpty(wxApp.getId())){
			map.put("msg", "保存失败，数据有误!");
			map.put("isCloseWindow", true);
			map.put("isRefresh", true);
			return "admin/msg";
		}
		try {
			wxAppService.add(wxApp);
			
			Site site = new Site();
			site.setId(wxApp.getId());
			site.setName(wxApp.getTitle());
			site.setSitedomain(wxApp.getDomain());
			siteService.insert(site);
			
			msg = oper + "公众号(" + wxApp.getTitle() + ")成功!";
		} catch (Exception e) {
			e.printStackTrace();
			msg = oper + "公众号(" + wxApp.getTitle() + ")失败:" + e.toString()
					+ "!";
		}
		operlogService.log(getLoginName(), msg, request);
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
		map.put("msg", "操作成功");
		map.put("forwardUrl", "wxapp/list.do?pageFuncId=" + pageFuncId);
		map.put("forwardSeconds", 3);
		return "admin/msg";
	}
}
