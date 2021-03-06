package com.gsccs.cms.controller.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gsccs.cms.bass.controller.CmsBaseController;
import com.gsccs.cms.bass.exception.AuthException;
import com.gsccs.cms.bass.exception.SiteCheckException;
import com.gsccs.cms.core.model.Content;
import com.gsccs.cms.core.model.Visit;
import com.gsccs.cms.core.service.VisitService;
import com.gsccs.cms.module.model.Comment;
import com.gsccs.cms.module.model.Consult;
import com.gsccs.cms.module.service.CommentService;
import com.gsccs.cms.module.service.ConsultService;

/**
 * 
 * 统计分析
 * 
 * @author x.d zhang
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/cms")
public class StatistController extends CmsBaseController {

	@Resource
	private VisitService visitService;
	@Resource
	private ConsultService guestbookService;
	@Resource
	private CommentService commentService;

	/**
	 * 工作量统计
	 * 
	 * @return
	 */
	@RequestMapping("/statWorkload.do")
	public String statWorkload(Content info, String export,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		info.setSite(getWxApp().getId());
		int sum = contentService.workloadSum(info);
		map.put("sum", sum);
		map.put("info", info);
		map.put("pageSize", pageSize);
		List<Content> infoList;
		if ("1".equals(export)) {
			infoList = contentService.workload(info);
			map.put("infoList", infoList);
			return "cms/workloadExport";
		} else {
			infoList = contentService.workload(info, currPage, pageSize);
			int totalCount = contentService.workloadCount(info);
			map.put("infoList", infoList);
			map.put("totalCount", totalCount);
			return "cms/workload";
		}
	}

	/**
	 * 系统 工作量统计
	 * 
	 * @return
	 */
	@RequestMapping("/statSysWorkload.do")
	public String statSysWorkload(Content info, String export,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {

		if (info == null) {
			info = new Content();
		}
		int sum = contentService.workloadSum(info);
		map.put("sum", sum);
		map.put("info", info);
		map.put("pageSize", pageSize);
		List<Content> infoList;
		if ("1".equals(export)) {
			infoList = contentService.workload(info);
			map.put("infoList", infoList);
			return "cms/sysWorkloadExport";
		} else {
			infoList = contentService.workload(info, currPage, pageSize);
			int totalCount = contentService.workloadCount(info);
			map.put("infoList", infoList);
			map.put("totalCount", totalCount);
			return "cms/sysWorkload";
		}
	}

	/**
	 * 系统 站点内容统计
	 * 
	 * @return
	 */
	@RequestMapping("/statSysSiteStat.do")
	public String statSysSiteStat(Content info, String export,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {

		if (info == null) {
			info = new Content();
		}
		int sum = contentService.siteStatSum(info);
		map.put("sum", sum);
		map.put("info", info);
		map.put("pageSize", pageSize);
		List<Content> infoList;
		if ("1".equals(export)) {
			infoList = contentService.siteStat(info);
			map.put("infoList", infoList);
			return "cms/sysSiteStatExport";
		} else {
			infoList = contentService.siteStat(info, currPage, pageSize);
			int totalCount = contentService.siteStatCount(info);
			map.put("infoList", infoList);
			map.put("totalCount", totalCount);
			return "cms/sysSiteStat";
		}
	}

	/**
	 * 栏目信息统计
	 * 
	 * @return
	 */
	@RequestMapping("/statChannelStat.do")
	public String statChannelStat(Content info, String export,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (info == null) {
			info = new Content();
		}
		info.setInfosite(getWxApp().getId());
		List<Content> infoList = contentService.channelStat(info);
		int sum = contentService.channelStatSum(info);
		map.put("sum", sum);
		map.put("info", info);
		map.put("infoList", infoList);
		if ("1".equals(export)) {
			return "cms/channelStatExport";
		} else {
			return "cms/channelStat";
		}
	}

	/**
	 * 信息更新统计
	 * 
	 * @return
	 */

	@RequestMapping("/statInfoUpdate.do")
	public String statInfoUpdate(Content info, String export,
			@RequestParam(defaultValue = "year") String statType,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (info == null) {
			info = new Content();
		}
		info.setInfosite(getWxApp().getId());
		int sum = 0;
		List<Content> infoList = null;
		map.put("info", info);
		map.put("statType", statType);
		if ("year".equals(statType)) {
			// 按年统计
			sum = contentService.infoUpdateYearSum(info);
			map.put("sum", sum);
			if ("1".equals(export)) {
				infoList = contentService.infoUpdateYear(info);
				map.put("infoList", infoList);
				return "cms/infoUpdateStatExport";
			} else {
				infoList = contentService.infoUpdateYear(info, currPage,
						pageSize);
				int totalCount = contentService.infoUpdateYearCount(info);
				map.put("infoList", infoList);
				map.put("totalCount", totalCount);
				return "cms/infoUpdateStat";
			}
		} else if ("month".equals(statType)) {
			// 按月统计
			sum = contentService.infoUpdateMonthSum(info);
			map.put("sum", sum);
			if ("1".equals(export)) {
				infoList = contentService.infoUpdateMonth(info);
				map.put("infoList", infoList);
				return "cms/infoUpdateStatExport";
			} else {
				infoList = contentService.infoUpdateMonth(info, currPage,
						pageSize);
				int totalCount = contentService.infoUpdateMonthCount(info);
				map.put("infoList", infoList);
				map.put("totalCount", totalCount);
				return "cms/infoUpdateStat";
			}
		} else if ("day".equals(statType)) {
			// 按日统计
			sum = contentService.infoUpdateDaySum(info);
			map.put("sum", sum);
			if ("1".equals(export)) {
				infoList = contentService.infoUpdateDay(info);
				map.put("infoList", infoList);
				return "cms/infoUpdateStatExport";
			} else {
				infoList = contentService.infoUpdateDay(info, currPage,
						pageSize);
				int totalCount = contentService.infoUpdateDayCount(info);
				map.put("infoList", infoList);
				map.put("totalCount", totalCount);
				return "cms/infoUpdateStat";
			}
		} else if ("week".equals(statType)) {
			// 按周统计
			sum = contentService.infoUpdateWeekSum(info);
			map.put("sum", sum);
			infoList = contentService.infoUpdateWeek(info);
			map.put("infoList", infoList);
			if ("1".equals(export)) {
				return "cms/infoUpdateStatExport";
			} else {
				return "cms/infoUpdateStat";
			}
		}
		return "cms/infoUpdateStat";
	}

	/**
	 * 系统信息更新统计
	 * 
	 * @return
	 */
	@RequestMapping("/statSysInfoUpdate.do")
	public String statSysInfoUpdate(Content content, String export,
			@RequestParam(defaultValue = "year") String statType,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (content == null) {
			content = new Content();
		}
		map.put("info", content);
		map.put("statType", statType);
		int sum = 0;
		int totalCount = 0;
		List<Content> infoList;
		if ("year".equals(statType)) {
			// 按年统计
			sum = contentService.infoUpdateYearSum(content);
			map.put("sum", sum);
			if ("1".equals(export)) {
				infoList = contentService.infoUpdateYear(content);
				map.put("infoList", infoList);
				return "cms/sysInfoUpdateStatExport";
			} else {
				infoList = contentService.infoUpdateYear(content, currPage,
						pageSize);
				totalCount = contentService.infoUpdateYearCount(content);
				map.put("infoList", infoList);
				map.put("totalCount", totalCount);
				return "cms/sysInfoUpdateStat";
			}
		} else if ("month".equals(statType)) {
			// 按月统计
			sum = contentService.infoUpdateMonthSum(content);
			map.put("sum", sum);
			if ("1".equals(export)) {
				infoList = contentService.infoUpdateMonth(content);
				map.put("infoList", infoList);
				return "cms/sysInfoUpdateStatExport";
			} else {
				infoList = contentService.infoUpdateMonth(content, currPage,
						pageSize);
				totalCount = contentService.infoUpdateMonthCount(content);
				map.put("infoList", infoList);
				map.put("totalCount", totalCount);
				return "cms/sysInfoUpdateStat";
			}
		} else if ("day".equals(statType)) {
			// 按日统计
			sum = contentService.infoUpdateDaySum(content);
			map.put("sum", sum);
			if ("1".equals(export)) {
				infoList = contentService.infoUpdateDay(content);
				map.put("infoList", infoList);
				return "cms/sysInfoUpdateStatExport";
			} else {
				infoList = contentService.infoUpdateDay(content, currPage,
						pageSize);
				totalCount = contentService.infoUpdateDayCount(content);
				map.put("infoList", infoList);
				map.put("totalCount", totalCount);
				return "cms/sysInfoUpdateStat";
			}
		} else if ("week".equals(statType)) {
			// 按周统计
			sum = contentService.infoUpdateWeekSum(content);
			map.put("sum", sum);
			infoList = contentService.infoUpdateWeek(content);
			map.put("infoList", infoList);
			if ("1".equals(export)) {
				return "cms/sysInfoUpdateStatExport";
			} else {
				return "cms/sysInfoUpdateStat";
			}
		}
		return "cms/sysInfoUpdateStat";
	}

	/**
	 * 栏目访问统计
	 * 
	 * @return
	 */

	@RequestMapping("/statChannelVisit.do")
	public String statChannelVisit(Visit visit, String export,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (visit == null) {
			visit = new Visit();
		}
		visit.setStatType("channel");
		visit.setSiteid(getWxApp().getId());
		int sum = visitService.channelVisitSum(visit);
		map.put("sum", sum);
		map.put("visit", visit);
		List<Visit> visitList;
		if ("1".equals(export)) {
			visitList = visitService.channelVisit(visit);
			map.put("visitList", visitList);
			return "cms/channelVisitExport";
		} else {
			visitList = visitService.channelVisit(visit, currPage, pageSize);
			int totalCount = visitService.channelVisitCount(visit);
			map.put("visitList", visitList);
			map.put("totalCount", totalCount);
			return "cms/channelVisit";
		}
	}

	/**
	 * 信息访问统计
	 * 
	 * @return
	 */

	@RequestMapping("/statInfoVisit.do")
	public String statInfoVisit(Visit visit, String export,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (visit == null) {
			visit = new Visit();
		}
		visit.setStatType("info");
		visit.setSiteid(getWxApp().getId());
		int sum = visitService.infoVisitSum(visit);
		map.put("sum", sum);
		map.put("visit", visit);
		List<Visit> visitList;
		if ("1".equals(export)) {
			visitList = visitService.infoVisit(visit);
			map.put("visitList", visitList);
			return "cms/infoVisitExport";
		} else {
			visitList = visitService.infoVisit(visit, currPage, pageSize);
			int totalCount = visitService.infoVisitCount(visit);
			map.put("visitList", visitList);
			map.put("totalCount", totalCount);
			return "cms/infoVisit";
		}
	}

	/**
	 * 访问趋势统计
	 * 
	 * @return
	 */

	@RequestMapping("/statVisit.do")
	public String statVisit(Visit visit, String export,
			@RequestParam(defaultValue = "year") String statType,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (visit == null) {
			visit = new Visit();
		}
		visit.setSiteid(getWxApp().getId());
		map.put("visit", visit);
		map.put("statType", statType);
		int sum = 0;
		int totalCount = 0;
		List<Visit> visitList;
		if ("year".equals(statType)) {
			// 按年统计
			sum = visitService.visitSum(visit);
			map.put("sum", sum);
			if ("1".equals(export)) {
				visitList = visitService.visitYear(visit);
				map.put("visitList", visitList);
				return "cms/visitStatExport";
			} else {
				visitList = visitService.visitYear(visit, currPage, pageSize);
				map.put("visitList", visitList);
				totalCount = visitService.visitYearCount(visit);
				map.put("totalCount", totalCount);
				return "cms/visitStat";
			}
		} else if ("month".equals(statType)) {
			// 按月统计
			sum = visitService.visitSum(visit);
			map.put("sum", sum);
			if ("1".equals(export)) {
				visitList = visitService.visitMonth(visit);
				map.put("visitList", visitList);
				return "cms/visitStatExport";
			} else {
				visitList = visitService.visitMonth(visit, currPage, pageSize);
				map.put("visitList", visitList);
				totalCount = visitService.visitMonthCount(visit);
				map.put("totalCount", totalCount);
				return "cms/visitStat";
			}
		} else if ("day".equals(statType)) {
			// 按日统计
			sum = visitService.visitSum(visit);
			map.put("sum", sum);
			if ("1".equals(export)) {
				visitList = visitService.visitDay(visit);
				map.put("visitList", visitList);
				return "cms/visitStatExport";
			} else {
				visitList = visitService.visitDay(visit, currPage, pageSize);
				map.put("visitList", visitList);
				totalCount = visitService.visitDayCount(visit);
				map.put("totalCount", totalCount);
				return "cms/visitStat";
			}
		} else if ("week".equals(statType)) {
			// 按周统计
			sum = visitService.visitSum(visit);
			map.put("sum", sum);
			visitList = visitService.visitWeek(visit);
			map.put("visitList", visitList);
			if ("1".equals(export)) {
				return "cms/visitStatExport";
			} else {
				return "cms/visitStat";
			}
		}
		return "cms/visitStat";
	}

	/**
	 * 访问趋势统计 系统
	 * 
	 * @return
	 */
	@RequestMapping("/statSysVisit.do")
	public String statSysVisit(Visit visit, String export,
			@RequestParam(defaultValue = "year") String statType,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (visit == null) {
			visit = new Visit();
		}
		map.put("visit", visit);
		map.put("statType", statType);
		int sum = 0;
		int totalCount = 0;
		List<Visit> visitList;
		if ("year".equals(statType)) {
			// 按年统计
			sum = visitService.visitSum(visit);
			map.put("sum", sum);
			if ("1".equals(export)) {
				visitList = visitService.visitYear(visit);
				map.put("visitList", visitList);
				return "cms/sysVisitStatExport";
			} else {
				visitList = visitService.visitYear(visit, currPage, pageSize);
				map.put("visitList", visitList);
				totalCount = visitService.visitYearCount(visit);
				map.put("totalCount", totalCount);
				return "cms/sysVisitStat";
			}
		} else if ("month".equals(statType)) {
			// 按月统计
			sum = visitService.visitSum(visit);
			map.put("sum", sum);
			if ("1".equals(export)) {
				visitList = visitService.visitMonth(visit);
				map.put("visitList", visitList);
				return "cms/sysVisitStatExport";
			} else {
				visitList = visitService.visitMonth(visit, currPage, pageSize);
				map.put("visitList", visitList);
				totalCount = visitService.visitMonthCount(visit);
				map.put("totalCount", totalCount);
				return "cms/sysVisitStat";
			}
		} else if ("day".equals(statType)) {
			// 按日统计
			sum = visitService.visitSum(visit);
			map.put("sum", sum);
			if ("1".equals(export)) {
				visitList = visitService.visitDay(visit);
				map.put("visitList", visitList);
				return "cms/sysVisitStatExport";
			} else {
				visitList = visitService.visitDay(visit, currPage, pageSize);
				map.put("visitList", visitList);
				totalCount = visitService.visitDayCount(visit);
				map.put("totalCount", totalCount);
				return "cms/sysVisitStat";
			}
		} else if ("week".equals(statType)) {
			// 按周统计
			sum = visitService.visitSum(visit);
			map.put("sum", sum);
			visitList = visitService.visitWeek(visit);
			map.put("visitList", visitList);
			if ("1".equals(export)) {
				return "cms/sysVisitStatExport";
			} else {
				return "cms/sysVisitStat";
			}
		}
		return "cms/sysVisitStat";
	}

	/**
	 * 留言频率统计
	 * 
	 * @return
	 */

	@RequestMapping("/statGuestbookUpdate.do")
	public String statGuestbookUpdate(Consult guestbook, String export,
			@RequestParam(defaultValue = "year") String statType,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (guestbook == null) {
			guestbook = new Consult();
		}
		map.put("guestbook", guestbook);
		map.put("statType", statType);
		int sum = 0;
		int totalCount = 0;
		List<Consult> guestbookList;
		guestbook.setSiteid(getWxApp().getId());
		if ("year".equals(statType)) {
			// 按年统计
			sum = guestbookService.guestbookUpdateYearSum(guestbook);
			map.put("sum", sum);
			if ("1".equals(export)) {
				guestbookList = guestbookService.guestbookUpdateYear(guestbook);
				map.put("guestbookList", guestbookList);
				return "cms/guestbookUpdateExport";
			} else {
				guestbookList = guestbookService.guestbookUpdateYear(guestbook,
						currPage, pageSize);
				map.put("guestbookList", guestbookList);
				totalCount = guestbookService
						.guestbookUpdateYearCount(guestbook);
				map.put("totalCount", totalCount);
				return "cms/guestbookUpdate";
			}
		} else if ("month".equals(statType)) {
			// 按月统计
			sum = guestbookService.guestbookUpdateMonthSum(guestbook);
			map.put("sum", sum);
			if ("1".equals(export)) {
				guestbookList = guestbookService
						.guestbookUpdateMonth(guestbook);
				map.put("guestbookList", guestbookList);
				return "cms/guestbookUpdateExport";
			} else {
				guestbookList = guestbookService.guestbookUpdateMonth(
						guestbook, currPage, pageSize);
				map.put("guestbookList", guestbookList);
				totalCount = guestbookService
						.guestbookUpdateMonthCount(guestbook);
				map.put("totalCount", totalCount);
				return "cms/guestbookUpdate";
			}
		} else if ("day".equals(statType)) {
			// 按日统计
			sum = guestbookService.guestbookUpdateDaySum(guestbook);
			map.put("sum", sum);
			if ("1".equals(export)) {
				guestbookList = guestbookService.guestbookUpdateDay(guestbook);
				map.put("guestbookList", guestbookList);
				return "cms/guestbookUpdateExport";
			} else {
				guestbookList = guestbookService.guestbookUpdateDay(guestbook,
						currPage, pageSize);
				map.put("guestbookList", guestbookList);
				totalCount = guestbookService
						.guestbookUpdateDayCount(guestbook);
				map.put("totalCount", totalCount);
				return "cms/guestbookUpdate";
			}
		} else if ("week".equals(statType)) {
			// 按周统计
			sum = guestbookService.guestbookUpdateWeekSum(guestbook);
			map.put("sum", sum);
			guestbookList = guestbookService.guestbookUpdateWeek(guestbook);
			map.put("guestbookList", guestbookList);
			if ("1".equals(export)) {
				return "cms/guestbookUpdateExport";
			} else {
				return "cms/guestbookUpdate";
			}
		}
		return "cms/guestbookUpdate";
	}

	/**
	 * 留言频率统计 系统
	 * 
	 * @return
	 */
	@RequestMapping("/statSysGuestbookUpdate.do")
	public String statSysGuestbookUpdate(Consult guestbook, String export,
			@RequestParam(defaultValue = "year") String statType,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (guestbook == null) {
			guestbook = new Consult();
		}
		map.put("guestbook", guestbook);
		map.put("statType", statType);
		int sum = 0;
		int totalCount = 0;
		List<Consult> guestbookList;
		if ("year".equals(statType)) {
			// 按年统计
			sum = guestbookService.guestbookUpdateYearSum(guestbook);
			map.put("sum", sum);
			if ("1".equals(export)) {
				guestbookList = guestbookService.guestbookUpdateYear(guestbook);
				map.put("guestbookList", guestbookList);
				return "cms/sysGuestbookUpdateExport";
			} else {
				guestbookList = guestbookService.guestbookUpdateYear(guestbook,
						currPage, pageSize);
				map.put("guestbookList", guestbookList);
				totalCount = guestbookService
						.guestbookUpdateYearCount(guestbook);
				map.put("totalCount", totalCount);
				return "cms/sysGuestbookUpdate";
			}
		} else if ("month".equals(statType)) {
			// 按月统计
			sum = guestbookService.guestbookUpdateMonthSum(guestbook);
			map.put("sum", sum);
			if ("1".equals(export)) {
				guestbookList = guestbookService
						.guestbookUpdateMonth(guestbook);
				map.put("guestbookList", guestbookList);
				return "cms/sysGuestbookUpdateExport";
			} else {
				guestbookList = guestbookService.guestbookUpdateMonth(
						guestbook, currPage, pageSize);
				map.put("guestbookList", guestbookList);
				totalCount = guestbookService
						.guestbookUpdateMonthCount(guestbook);
				map.put("totalCount", totalCount);
				return "cms/sysGuestbookUpdate";
			}
		} else if ("day".equals(statType)) {
			// 按日统计
			sum = guestbookService.guestbookUpdateDaySum(guestbook);
			map.put("sum", sum);
			if ("1".equals(export)) {
				guestbookList = guestbookService.guestbookUpdateDay(guestbook);
				map.put("guestbookList", guestbookList);
				return "cms/sysGuestbookUpdateExport";
			} else {
				guestbookList = guestbookService.guestbookUpdateDay(guestbook,
						currPage, pageSize);
				map.put("guestbookList", guestbookList);
				totalCount = guestbookService
						.guestbookUpdateDayCount(guestbook);
				map.put("totalCount", totalCount);
				return "cms/sysGuestbookUpdate";
			}
		} else if ("week".equals(statType)) {
			// 按周统计
			sum = guestbookService.guestbookUpdateWeekSum(guestbook);
			map.put("sum", sum);
			guestbookList = guestbookService.guestbookUpdateWeek(guestbook);
			map.put("guestbookList", guestbookList);
			if ("1".equals(export)) {
				return "cms/sysGuestbookUpdateExport";
			} else {
				return "cms/sysGuestbookUpdate";
			}
		}
		return "cms/sysGuestbookUpdate";
	}

	/**
	 * 系统 站点留言统计
	 * 
	 * @return
	 */
	@RequestMapping("/statSysSiteGuestbook.do")
	public String statSysSiteGuestbook(Consult guestbook, String export,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (guestbook == null) {
			guestbook = new Consult();
		}
		map.put("guestbook", guestbook);
		int sum = 0;
		int totalCount = 0;
		List<Consult> guestbookList;
		sum = guestbookService.sysSiteGuestbookSum(guestbook);
		map.put("sum", sum);
		if ("1".equals(export)) {
			guestbookList = guestbookService.sysSiteGuestbook(guestbook);
			map.put("guestbookList", guestbookList);
			return "cms/sysSiteGuestbookExport";
		} else {
			guestbookList = guestbookService.sysSiteGuestbook(guestbook,
					currPage, pageSize);
			map.put("guestbookList", guestbookList);
			totalCount = guestbookService.sysSiteGuestbookCount(guestbook);
			map.put("totalCount", totalCount);
			return "cms/sysSiteGuestbook";
		}
	}

	/**
	 * 评论频率统计
	 * 
	 * @return
	 */

	@RequestMapping("/statCommentUpdate.do")
	public String statCommentUpdate(Comment comment, String export,
			@RequestParam(defaultValue = "year") String statType,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (comment == null) {
			comment = new Comment();
		}
		Map<String, String> objtypes = comment.getObjtypes();
		comment.setSiteid(getWxApp().getId());
		map.put("objtypes", objtypes);
		map.put("statType", statType);
		map.put("comment", comment);
		int sum = 0;
		int totalCount = 0;
		List<Comment> commentList;
		if ("year".equals(statType)) {
			// 按年统计
			sum = commentService.commentUpdateYearSum(comment);
			map.put("sum", sum);
			if ("1".equals(export)) {
				commentList = commentService.commentUpdateYear(comment);
				map.put("commentList", commentList);
				return "cms/commentUpdateExport";
			} else {
				commentList = commentService.commentUpdateYear(comment,
						currPage, pageSize);
				map.put("commentList", commentList);
				totalCount = commentService.commentUpdateYearCount(comment);
				map.put("totalCount", totalCount);
				return "cms/commentUpdate";
			}
		} else if ("month".equals(statType)) {
			// 按月统计
			sum = commentService.commentUpdateMonthSum(comment);
			map.put("sum", sum);
			if ("1".equals(export)) {
				commentList = commentService.commentUpdateMonth(comment);
				map.put("commentList", commentList);
				return "cms/commentUpdateExport";
			} else {
				commentList = commentService.commentUpdateMonth(comment,
						currPage, pageSize);
				map.put("commentList", commentList);
				totalCount = commentService.commentUpdateMonthCount(comment);
				map.put("totalCount", totalCount);
				return "cms/commentUpdate";
			}
		} else if ("day".equals(statType)) {
			// 按日统计
			sum = commentService.commentUpdateDaySum(comment);
			map.put("sum", sum);
			if ("1".equals(export)) {
				commentList = commentService.commentUpdateDay(comment);
				map.put("commentList", commentList);
				return "cms/commentUpdateExport";
			} else {
				commentList = commentService.commentUpdateDay(comment,
						currPage, pageSize);
				map.put("commentList", commentList);
				totalCount = commentService.commentUpdateDayCount(comment);
				map.put("totalCount", totalCount);
				return "cms/commentUpdate";
			}
		} else if ("week".equals(statType)) {
			// 按周统计
			sum = commentService.commentUpdateWeekSum(comment);
			map.put("sum", sum);
			commentList = commentService.commentUpdateWeek(comment);
			map.put("commentList", commentList);
			if ("1".equals(export)) {
				return "cms/commentUpdateExport";
			} else {
				return "cms/commentUpdate";
			}
		}
		return "cms/commentUpdate";
	}

	/**
	 * 评论频率统计 系统
	 * 
	 * @return
	 */
	@RequestMapping("/statSysCommentUpdate.do")
	public String statSysCommentUpdate(Comment comment, String export,
			@RequestParam(defaultValue = "year") String statType,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (comment == null) {
			comment = new Comment();
		}
		Map<String, String> objtypes = comment.getObjtypes();
		map.put("statType", statType);
		map.put("objtypes", objtypes);
		map.put("comment", comment);
		int sum = 0;
		int totalCount = 0;
		List<Comment> commentList;
		if ("year".equals(statType)) {
			// 按年统计
			sum = commentService.commentUpdateYearSum(comment);
			map.put("sum", sum);
			if ("1".equals(export)) {
				commentList = commentService.commentUpdateYear(comment);
				map.put("commentList", commentList);
				return "cms/sysCommentUpdateExport";
			} else {
				commentList = commentService.commentUpdateYear(comment,
						currPage, pageSize);
				map.put("commentList", commentList);
				totalCount = commentService.commentUpdateYearCount(comment);
				map.put("totalCount", totalCount);
				return "cms/sysCommentUpdate";
			}
		} else if ("month".equals(statType)) {
			// 按月统计
			sum = commentService.commentUpdateMonthSum(comment);
			map.put("sum", sum);
			if ("1".equals(export)) {
				commentList = commentService.commentUpdateMonth(comment);
				map.put("commentList", commentList);
				return "cms/sysCommentUpdateExport";
			} else {
				commentList = commentService.commentUpdateMonth(comment,
						currPage, pageSize);
				map.put("commentList", commentList);
				totalCount = commentService.commentUpdateMonthCount(comment);
				map.put("totalCount", totalCount);
				return "cms/sysCommentUpdate";
			}
		} else if ("day".equals(statType)) {
			// 按日统计
			sum = commentService.commentUpdateDaySum(comment);
			map.put("sum", sum);
			if ("1".equals(export)) {
				commentList = commentService.commentUpdateDay(comment);
				map.put("commentList", commentList);
				return "cms/sysCommentUpdateExport";
			} else {
				commentList = commentService.commentUpdateDay(comment,
						currPage, pageSize);
				map.put("commentList", commentList);
				totalCount = commentService.commentUpdateDayCount(comment);
				map.put("totalCount", totalCount);
				return "cms/sysCommentUpdate";
			}
		} else if ("week".equals(statType)) {
			// 按周统计
			sum = commentService.commentUpdateWeekSum(comment);
			map.put("sum", sum);
			commentList = commentService.commentUpdateWeek(comment);
			map.put("commentList", commentList);
			if ("1".equals(export)) {
				return "cms/sysCommentUpdateExport";
			} else {
				return "cms/sysCommentUpdate";
			}
		}
		return "cms/sysCommentUpdate";
	}

	/**
	 * 系统 站点评论统计
	 * 
	 * @return
	 */
	@RequestMapping("/statSysSiteComment.do")
	public String statSysSiteComment(Comment comment, String export,
			@RequestParam(defaultValue = "") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (comment == null) {
			comment = new Comment();
		}
		Map<String, String> objtypes = comment.getObjtypes();
		map.put("objtypes", objtypes);
		map.put("comment", comment);
		int sum = 0;
		int totalCount = 0;
		List<Comment> commentList;
		sum = commentService.sysSiteCommentSum(comment);
		map.put("sum", sum);
		if ("1".equals(export)) {
			commentList = commentService.sysSiteComment(comment);
			map.put("commentList", commentList);
			return "cms/sysSiteCommentExport";
		} else {
			commentList = commentService.sysSiteComment(comment, currPage,
					pageSize);
			map.put("commentList", commentList);
			totalCount = commentService.sysSiteCommentCount(comment);
			map.put("totalCount", totalCount);
			return "cms/sysSiteComment";
		}
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
