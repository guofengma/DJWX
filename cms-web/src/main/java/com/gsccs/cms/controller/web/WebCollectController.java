package com.gsccs.cms.controller.web;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gsccs.cms.bass.utils.ResponseUtil;
import com.gsccs.cms.core.model.Channel;
import com.gsccs.cms.core.model.Content;
import com.gsccs.cms.core.model.Site;
import com.gsccs.cms.core.service.ChannelService;
import com.gsccs.cms.core.service.ContentService;
import com.gsccs.cms.core.service.SiteService;
import com.gsccs.cms.module.model.Collect;
import com.gsccs.cms.module.service.CollectService;

/**
 * 收藏相关操作
 * 
 * @author x.d zhang
 * @version 1.0
 * 
 */
@Controller("WebCollectController")
@RequestMapping("/web/{appid}")
public class WebCollectController{
	
	@Resource
	private SiteService siteService;
	@Resource
	private CollectService collectService;
	@Resource
	private ContentService contentService;
	@Resource
	private ChannelService channelService;
	
	
	/**
	 * 会员收藏页面
	 * @param appid
	 * @param page
	 * @param pagesize
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/collect.html")
	public String collect(@PathVariable String appid,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "500") int pagesize,
			ModelMap map,
			HttpServletRequest request) {
		java.util.List<Collect> list = null;
		String openid = request.getParameter("openid");
		if (StringUtils.isNotEmpty(openid)){
			Collect param = new Collect();
			param.setMemberid(openid);
			param.setSiteid(appid);
			list = collectService.find(param, "addtime desc", page, pagesize);
		}
		map.put("list", list);
		return "web/collect";
	}
	
	
	/**
	 * ajax收藏
	 * 
	 * @return
	 */
	@RequestMapping("/collectDo.html")
	public String collectDo(String openid,String infoid, ModelMap map,
			HttpServletResponse response) {
		String msg = "";
		
		if (StringUtils.isEmpty(openid)){
			msg = "请先登录";
			return msg;
		}
		
		Collect collect = new Collect();
		collect.setMemberid(openid);
		collect.setArticleid(infoid);
		try {
			// 查询是否已收藏
			if (collectService.count(collect) > 0) {
				msg = "您已收藏";
			} else {
				// 查询收藏对象
				collect.setAddtime(new Date());
				collectService.insert(collect);
				msg = "收藏成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "收藏失败";
		}
		ResponseUtil.writeUTF(response, msg);
		return null;
	}

}
