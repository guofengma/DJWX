package com.gsccs.cms.controller.core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gsccs.cms.auth.model.Config;
import com.gsccs.cms.auth.utils.AuthConst;
import com.gsccs.cms.bass.controller.CmsBaseController;
import com.gsccs.cms.bass.exception.AuthException;
import com.gsccs.cms.bass.exception.SiteCheckException;
import com.gsccs.cms.bass.utils.FileUtil;
import com.gsccs.cms.bass.utils.HtmlCode;
import com.gsccs.cms.bass.utils.Pager;
import com.gsccs.cms.core.model.Link;
import com.gsccs.cms.core.model.Site;
import com.gsccs.cms.core.service.LinkService;
import com.gsccs.cms.core.service.SiteService;
import com.gsccs.cms.core.util.CmsConst;

/**
 * 
 * 关于链接的相关操作
 * 
 * @author x.d zhang
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/cms")
public class LinkController extends CmsBaseController {
	@Resource
	private LinkService linkService;
	@Resource
	private SiteService siteService;

	/**
	 * 链接类别
	 * 
	 * @return
	 */
	@RequestMapping("/linkClazz.do")
	public String linkClazz(Link link,
			@RequestParam(defaultValue = " ordernum ") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		// 只有选择站点才查询
		Subject subject = SecurityUtils.getSubject();
		if (!subject.hasRole(AuthConst.SYS_ADMIN)) {
			link.setSite(getWxApp().getId());
		}
		if (link != null && link.getSite() != null
				&& link.getSite().trim().length() > 0) {
			link.setIsClass("1");
			List<Link> linkList = linkService.find(link, order, currPage,
					pageSize);
			int totalCount = linkService.count(link);
			Pager pager = new Pager(request);
			pager.appendParam("site");
			pager.appendParam("type");
			pager.appendParam("name");
			pager.appendParam("pagemark");
			pager.appendParam("pageSize");
			pager.appendParam("pageFuncId");
			pager.setCurrPage(currPage);
			pager.setPageSize(pageSize);
			pager.setTotalCount(totalCount);
			pager.setOutStrBootstrap("linkClazz.do");
			map.put("pageStr", pager.getOutStrBootstrap());
			map.put("list", linkList);
			map.put("order", order);
			map.put("link", link);
		}
		return "cms/linkClazz";
	}

	/**
	 * 链接类别 编辑页面
	 * 
	 * @return
	 */

	@RequestMapping("/linkClazzEdit.do")
	public String linkClazzEdit(Link link, ModelMap map,
			HttpServletResponse response) {
		if (link != null && link.getId() != null
				&& link.getId().trim().length() > 0) {
			link = linkService.findById(link.getId());
			map.put("link", link);
		}
		return "cms/linkClazzEdit";
	}

	/**
	 * 链接类别 编辑
	 * 
	 * @return
	 */

	@RequestMapping("/linkClazzEditDo.do")
	public String linkClazzEditDo(Link link, ModelMap map,
			HttpServletResponse response) {
		String oper = "添加";
		try {
			if (link != null && StringUtils.isNotEmpty(link.getId())) {
				Link oldlink = linkService.findById(link.getId());
				if (oldlink != null) {
					// 如果原来有和现在的pagemark不同则判断新的pagemark是否存在
					if (link.getPagemark() != null
							&& link.getPagemark().trim().length() > 0
							&& oldlink.getPagemark() != null
							&& !oldlink.getPagemark()
									.equals(link.getPagemark())) {
						if (linkService.hasPagemark(oldlink.getSite(),
								link.getType(), true, link.getPagemark())) {
							map.put("msg", "此页面标识已存在!");
							map.put("isBack", true);
							return "admin/msg";
						}
					}
					oldlink.setName(link.getName());
					oldlink.setOrdernum(link.getOrdernum());
					oldlink.setIsok(link.getIsok());
					oldlink.setImg(link.getImg());
					oldlink.setPagemark(link.getPagemark());
					oper = "修改";
					linkService.update(oldlink);
				}
			} else {
				link.setSite(getWxApp().getId());
				// 添加
				// 判断页面标识是否已存在
				if (link.getPagemark() != null
						&& link.getPagemark().trim().length() > 0
						&& linkService.hasPagemark(link.getSite(),
								link.getType(), true, link.getPagemark())) {
					map.put("msg", "此页面标识已存在!");
					map.put("isBack", true);
					return "admin/msg";
				}
				linkService.add(link);
			}
			msg = oper + "链接分类(" + link.getName() + ")成功!";
		} catch (Exception e) {
			e.printStackTrace();
			msg = oper + "链接分类(" + link.getName() + ")失败:" + e.toString() + "!";
		}
		operlogService.log(getLoginName(), msg, request);
		map.put("msg", msg);
		map.put("isCloseWindow", true);
		map.put("isRefresh", true);
		return "admin/msg";
	}

	/**
	 * 链接类别 删除
	 * 
	 * @return
	 */
	@RequestMapping("/linkClazzDel.do")
	public String linkClazzDel(String pageFuncId, String ids, ModelMap map,
			HttpServletResponse response) {
		String type = "";
		if (ids != null && ids.trim().length() > 0) {
			String[] idArr = ids.split(";");
			if (idArr != null && idArr.length > 0) {
				Link link = null;
				for (int i = 0; i < idArr.length; i++) {
					if (idArr[i].trim().length() > 0) {
						try {
							link = linkService.findById(idArr[i]);
							if (link != null) {
								type = link.getType();
								linkService.delClass(idArr[i]);
								msg = "删除链接分类(" + link.getName() + ")成功!";
							}
						} catch (Exception e) {
							e.printStackTrace();
							msg = "删除链接分类(" + link.getName() + ")失败:"
									+ e.toString() + "!";
						}
						operlogService.log(getLoginName(), msg, request);
					}
				}
			}
		}
		map.put("msg", "操作成功");
		map.put("forwardUrl", "linkClazz.do?pageFuncId=" + pageFuncId
				+ "&type=" + type);
		map.put("forwardSeconds", 3);
		return "admin/msg";
	}

	/**
	 * 链接
	 * 
	 * @return
	 */

	@RequestMapping("/link.do")
	public String link(Link link,
			@RequestParam(defaultValue = " ordernum ") String order,
			@RequestParam(defaultValue = "1") int currPage,
			@RequestParam(defaultValue = "10") int pageSize, ModelMap map,
			HttpServletResponse response) {
		if (link == null) {
			link = new Link();
		}
		if (link.getSite() == null || link.getSite().trim().length() == 0) {
			link.setSite(getWxApp().getId());
		}
		// 只有选择站点才查询
		if (link != null && link.getSite() != null
				&& link.getSite().trim().length() > 0) {
			if (order.trim().length() == 0) {
				order = " ordernum ";
			}
			List<Link> linkList = linkService.find(link, order, currPage,
					pageSize);
			int totalCount = linkService.count(link);
			Pager pager = new Pager(request);
			pager.appendParam("site");
			pager.appendParam("name");
			pager.appendParam("className");
			pager.appendParam("pagemark");
			pager.appendParam("type");
			pager.appendParam("pageSize");
			pager.appendParam("pageFuncId");
			pager.setCurrPage(currPage);
			pager.setPageSize(pageSize);
			pager.setTotalCount(totalCount);
			pager.setOutStrBootstrap("link.do");
			map.put("pageStr", pager.getOutStrBootstrap());
			map.put("list", linkList);
			map.put("order", order);
			map.put("link", link);
		}
		return "cms/link";
	}

	/**
	 * 链接 编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/linkEdit.do")
	public String linkEdit(Link link, ModelMap map, HttpServletResponse response) {
		if (link != null && link.getId() != null
				&& link.getId().trim().length() > 0) {
			link = linkService.findById(link.getId());
			if (link != null) {
				Link linkClass = linkService.findById(link.getParid());
				map.put("linkClass", linkClass);
			}
			map.put("link", link);
		}
		// 提取站点的链接分类
		Link classLink = new Link();
		classLink.setSite(getWxApp().getId());
		classLink.setIsClass("1");
		classLink.setType(link.getType());
		List<Link> linkList = linkService.findAll(classLink, " ordernum ");
		map.put("linkList", linkList);
		return "cms/linkEdit";
	}

	/**
	 * 链接 编辑
	 * 
	 * @return
	 */
	@RequestMapping("/linkEditDo.do")
	public String linkEditDo(
			Link link,
			@RequestParam(value = "imgfile", required = false) MultipartFile imgfile,
			String oldImg, ModelMap map, HttpServletResponse response) {
		String oper = "添加";
		Config sitePathConfig = configService
				.findByCode(CmsConst.SITE_ROOT_PATH_CODE);
		String siteRoot = sitePathConfig.getCode();
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
		String datapath = formater.format(new Date());
		Site site = siteService.findById(getWxApp().getId());
		String filePath = siteRoot + File.separator + site.getSourcepath()
				+ File.separator + "upload" + File.separator + datapath;
		try {
			link.setSite(site.getId());
			link.setUrl(HtmlCode.url(link.getUrl()));
			if (link != null && StringUtils.isNotEmpty(link.getId())) {
				Link oldlink = linkService.findById(link.getId());
				if (oldlink != null) {
					// 如果原来有和现在的pagemark不同则判断新的pagemark是否存在
					if (link.getPagemark() != null
							&& link.getPagemark().trim().length() > 0
							&& oldlink.getPagemark() != null
							&& !oldlink.getPagemark()
									.equals(link.getPagemark())) {
						if (linkService.hasPagemark(link.getSite(),
								link.getType(), false, link.getPagemark())) {
							map.put("msg", "此页面标识已存在!");
							map.put("isBack", true);
							return "admin/msg";
						}
					}
					// 2014-8-8 修改 对访问地址进行判断
					String linkUrl = link.getUrl().startsWith("http://") ? link
							.getUrl() : "http://" + link.getUrl();
					oldlink.setParid(link.getParid());
					oldlink.setUrl(linkUrl);
					oldlink.setName(link.getName());
					oldlink.setOrdernum(link.getOrdernum());
					oldlink.setIsok(link.getIsok());
					// 如果原来有和现在的logo不同则删除原来的logo文件
					if (!oldImg.equals(oldlink.getImg())
							&& oldlink.getImg() != null
							&& oldlink.getImg().trim().length() > 0) {
						FileUtil.del(siteRoot + File.separator
								+ oldlink.getImg().trim());
					}
					oldlink.setImg(oldImg);
					if (imgfile != null && imgfile.getSize() > 0) {
						// 生成目标文件
						String root = request.getRealPath("/");
						String ext = FileUtil.getExt(
								imgfile.getOriginalFilename()).toLowerCase();
						if (!".jpg".equals(ext) && !".jpeg".equals(ext)
								&& !".gif".equals(ext) && !".png".equals(ext)) {
							map.put("msg", "图片只能上传jpg,jpeg,gif,png格式的图片!");
							map.put("isBack", true);
							return "admin/msg";
						}
						String id = UUID.randomUUID().toString();
						File targetFile = new File(filePath + File.separator
								+ id + ext);
						File folder = new File(filePath);
						if (!folder.exists()) {
							folder.mkdir();
						}
						if (!targetFile.exists()) {
							targetFile.createNewFile();
						}
						// 复制到目标文件
						FileUtil.copy(imgfile, targetFile);
						// 生成访问地址
						oldlink.setImg(site.getSourcepath() + File.separator
								+ "upload" + File.separator + datapath
								+ File.separator + id + ext);
					}
					oldlink.setPagemark(link.getPagemark());
					oper = "修改";
					linkService.update(oldlink);
				}
			} else {
				// 添加
				// 判断页面标识是否已存在
				if (link.getPagemark() != null
						&& link.getPagemark().trim().length() > 0
						&& linkService.hasPagemark(link.getSite(),
								link.getType(), false, link.getPagemark())) {
					map.put("msg", "此页面标识已存在!");
					map.put("isBack", true);
					return "admin/msg";
				}

				// site.getSitedomain().startsWith("http://")?site.getSitedomain():"http://"+site.getSitedomain()
				String linkUrl = link.getUrl().startsWith("http://") ? link
						.getUrl() : "http://" + link.getUrl();
				link.setUrl(linkUrl);
				if (imgfile != null && imgfile.getSize() > 0) {
					// 生成目标文件 2014-8-7修改

					// String root = request.getRealPath("/");
					String ext = FileUtil.getExt(imgfile.getOriginalFilename())
							.toLowerCase();
					if (!".jpg".equals(ext) && !".jpeg".equals(ext)
							&& !".gif".equals(ext) && !".png".equals(ext)) {
						map.put("msg", "图片只能上传jpg,jpeg,gif,png格式的图片!");
						map.put("isBack", true);
						return "admin/msg";
					}
					String id = UUID.randomUUID().toString();

					File targetFile = new File(filePath + File.separator + id
							+ ext);
					File folder = new File(filePath);
					/*
					 * File targetFile = new File(root + "/upload/" +
					 * link.getSite() + "/" + id + ext); File folder = new
					 * File(root + "/upload/" + link.getSite() + "/");
					 */

					System.out.println("link===" + filePath);
					if (!folder.exists()) {
						folder.mkdir();
					}
					if (!targetFile.exists()) {
						targetFile.createNewFile();
					}
					// 复制到目标文件
					FileUtil.copy(imgfile, targetFile);
					// 生成访问地址
					link.setImg(site.getSourcepath() + File.separator
							+ "upload" + File.separator + datapath
							+ File.separator + id + ext);
				}
				linkService.add(link);
			}
			msg = oper + "链接(" + link.getName() + ")成功!";
		} catch (Exception e) {
			e.printStackTrace();
			msg = oper + "链接(" + link.getName() + ")失败:" + e.toString() + "!";
		}
		operlogService.log(getLoginName(), msg, request);
		map.put("msg", msg);
		map.put("isCloseWindow", true);
		map.put("isRefresh", true);
		return "admin/msg";
	}

	/**
	 * 链接 删除
	 * 
	 * @return
	 */
	@RequestMapping("/linkDel.do")
	public String linkDel(String pageFuncId, String ids, ModelMap map,
			HttpServletResponse response) {
		String type = "";
		if (ids != null && ids.trim().length() > 0) {
			String[] idArr = ids.split(";");
			if (idArr != null && idArr.length > 0) {
				Link link = null;
				for (int i = 0; i < idArr.length; i++) {
					if (idArr[i].trim().length() > 0) {
						try {
							link = linkService.findById(idArr[i]);
							if (link != null) {
								type = link.getType();
								linkService.del(idArr[i]);
								msg = "删除链接(" + link.getName() + ")成功!";
							}
						} catch (Exception e) {
							e.printStackTrace();
							msg = "删除链接(" + link.getName() + ")失败:"
									+ e.toString() + "!";
						}
						operlogService.log(getLoginName(), msg, request);
					}
				}
			}
		}
		map.put("msg", "操作成功");
		map.put("forwardUrl", "link.do?pageFuncId=" + pageFuncId + "&type="
				+ type);
		map.put("forwardSeconds", 3);
		return "admin/msg";
	}
}
