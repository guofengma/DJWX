package com.gsccs.cms.weixin.service;

import java.util.List;
import java.util.Map;

import com.gsccs.cms.weixin.model.WxApp;

public interface WxAppService {

	/**
	 * 分页查询
	 */
	public List<WxApp> find(WxApp param, String order, int currPage,
			int pageSize, boolean iscache);

	public int count(WxApp param);

	/**
	 * 查询
	 */
	public List<WxApp> find(WxApp param, String order);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @param cache
	 * @return
	 */
	public WxApp findById(String id);

	/**
	 * 更新
	 * 
	 * @param WxApp
	 */
	public void update(WxApp wxApp);

	/**
	 * 添加
	 * 
	 * @param wxApp
	 * @return
	 */
	public void add(WxApp wxApp);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void del(String id);

}
