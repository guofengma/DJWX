package com.gsccs.cms.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gsccs.cms.auth.service.ConfigService;
import com.gsccs.cms.auth.service.OperlogService;
import com.gsccs.cms.bass.utils.SqlUtil;
import com.gsccs.cms.core.dao.ContentMapper;
import com.gsccs.cms.core.model.Content;
import com.gsccs.cms.core.model.ContentExample;
import com.gsccs.cms.core.model.ContentExample.Criteria;
import com.gsccs.cms.core.service.ChannelService;
import com.gsccs.cms.core.service.ContentService;
import com.gsccs.cms.core.service.SiteService;

/**
 * 网站内容相关服务
 * 
 * @author x.d zhang
 * @version 1.0
 * 
 */
@Service("contentService")
public class ContentServiceImpl implements ContentService {

	@Resource
	private ContentMapper contentMapper;
	@Resource
	private ChannelService channelService;
	@Resource
	private SiteService siteService;
	@Resource
	private OperlogService operlogService;
	@Resource
	private ConfigService configService;

	
	/**
	 * 分页查询
	 * 
	 * @param info
	 * @param order
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List<Content> find(Content info, String order, int currPage,
			int pageSize) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		if (order != null && order.trim().length() > 0) {
			example.setOrderByClause(order);
		}
		example.setCurrPage(currPage);
		example.setPageSize(pageSize);
		return contentMapper.selectPageByExample(example);
	}

	/**
	 * 查询所有
	 * 
	 * @param info
	 * @param order
	 * @return
	 */
	public List<Content> findAll(Content info, String order) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		if (order != null && order.trim().length() > 0) {
			example.setOrderByClause(order);
		}
		return contentMapper.selectByExample(example);
	}

	/**
	 * 统计
	 * 
	 * @param info
	 * @return
	 */
	public int count(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.countByExample(example);
	}

	/**
	 * 处理查询条件
	 * 
	 * @param info
	 * @param criteria
	 */
	@SuppressWarnings("deprecation")
	public void proSearchParam(Content info, Criteria criteria) {
		if (info != null) {
			if (info.getId() != null) {
				criteria.andInfoIdEqualTo(info.getId());
			}
			if (info.getSite() != null && info.getSite().trim().length() > 0) {
				criteria.andInfoSiteEqualTo(info.getSite());
			}

			if (info.getChannel() != null) {
				criteria.andChannelEqualTo(info.getChannel());
			}
			
			if (info.getChannelids() != null && info.getChannelids().size() > 0) {
				criteria.andChannelIn(info.getChannelids());
			}
			if (info.getAddtime() != null) {
				Date date1=info.getAddtime();
				Date date2=new Date();
				date1.setHours(0);
				date1.setMinutes(0);
				date1.setSeconds(1);
				date2.setYear(date1.getYear());
				date2.setMonth(date1.getMonth());
				date2.setDate(date1.getDate());
				date2.setHours(23);
				date2.setMinutes(59);
				date2.setSeconds(59);
				criteria.andAddtimeBetween(date1 ,date2);
			}

			info.setTitle(SqlUtil.replace(info.getTitle()));
			if (info.getTitle() != null && info.getTitle().trim().length() > 0) {
				criteria.andTitleLike("%" + info.getTitle().trim() + "%");
			}
			if (info.getIstop() != null && info.getIstop().trim().length() > 0) {
				criteria.andIstopEqualTo(info.getIstop());
			}
			
			if (info.getChannels() != null && info.getChannels().length > 0) {
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < info.getChannels().length; i++) {
					idList.add(info.getChannels()[i]);
				}
				criteria.andChannelIn(idList);
			}
			

		}
	}

	/**
	 * 添加
	 * 
	 * @param info
	 * @return
	 */
	public Long insert(Content content) {
		if (content != null) {
			content.setAddtime(new Date());
			contentMapper.insert(content);
			return content.getId();
		}
		return null;
	}

	/**
	 * 更新
	 * 
	 * @param info
	 */
	public void update(Content content) {
		contentMapper.updateByPrimaryKeyWithBLOBs(content);
	}

	/**
	 * 点击
	 * 
	 * @param info
	 * @return
	 */
	public void click(Content info) {
		contentMapper.click(info);
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public Content findById(Long id) {
		return contentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	public Content findClickById(String id) {
		return contentMapper.selectClickByPrimaryKey(id);
	}

	public void del(String id) {
		contentMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 工作量统计
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> workload(Content info, int currPage, int pageSize) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		example.setCurrPage(currPage);
		example.setPageSize(pageSize);
		return contentMapper.workloadPage(example);
	}

	/**
	 * 工作量统计
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> workload(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.workload(example);
	}

	/**
	 * 工作量统计
	 * 
	 * @param info
	 * @return
	 */
	public int workloadCount(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.workloadCount(example);
	}

	/**
	 * 工作量合计
	 * 
	 * @param info
	 * @return
	 */
	public int workloadSum(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.workloadSum(example);
	}

	/**
	 * 站点内容统计
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> siteStat(Content info, int currPage, int pageSize) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		example.setCurrPage(currPage);
		example.setPageSize(pageSize);
		return contentMapper.siteStatPage(example);
	}

	/**
	 * 站点内容统计
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> siteStat(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.siteStat(example);
	}

	/**
	 * 站点内容统计
	 * 
	 * @param info
	 * @return
	 */
	public int siteStatCount(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.siteStatCount(example);
	}

	/**
	 * 站点内容合计
	 * 
	 * @param info
	 * @return
	 */
	public int siteStatSum(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.siteStatSum(example);
	}

	/**
	 * 栏目信息统计
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> channelStat(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.channelStat(example);
	}

	/**
	 * 栏目信息统计
	 * 
	 * @param info
	 * @return
	 */
	public int channelStatCount(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.channelStatCount(example);
	}

	/**
	 * 栏目信息合计
	 * 
	 * @param info
	 * @return
	 */
	public int channelStatSum(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.channelStatSum(example);
	}

	/**
	 * 信息更新统计 年
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> infoUpdateYear(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateYear(example);
	}

	/**
	 * 信息更新统计 年
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> infoUpdateYear(Content info, int currPage, int pageSize) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		example.setCurrPage(currPage);
		example.setPageSize(pageSize);
		return contentMapper.infoUpdateYearPage(example);
	}

	/**
	 * 信息更新统计 年
	 * 
	 * @param info
	 * @return
	 */
	public int infoUpdateYearCount(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateYearCount(example);
	}

	/**
	 * 信息更新合计 年
	 * 
	 * @param info
	 * @return
	 */
	public int infoUpdateYearSum(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateYearSum(example);
	}

	/**
	 * 信息更新统计 月
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> infoUpdateMonth(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateMonth(example);
	}

	/**
	 * 信息更新统计 月
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> infoUpdateMonth(Content info, int currPage,
			int pageSize) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		example.setCurrPage(currPage);
		example.setPageSize(pageSize);
		return contentMapper.infoUpdateMonthPage(example);
	}

	/**
	 * 信息更新统计 月
	 * 
	 * @param info
	 * @return
	 */
	public int infoUpdateMonthCount(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateMonthCount(example);
	}

	/**
	 * 信息更新合计 月
	 * 
	 * @param info
	 * @return
	 */
	public int infoUpdateMonthSum(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateMonthSum(example);
	}

	/**
	 * 信息更新统计 日
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> infoUpdateDay(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateDay(example);
	}

	/**
	 * 信息更新统计 日
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> infoUpdateDay(Content info, int currPage, int pageSize) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		example.setCurrPage(currPage);
		example.setPageSize(pageSize);
		return contentMapper.infoUpdateDayPage(example);
	}

	/**
	 * 信息更新统计 日
	 * 
	 * @param info
	 * @return
	 */
	public int infoUpdateDayCount(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateDayCount(example);
	}

	/**
	 * 信息更新合计 日
	 * 
	 * @param info
	 * @return
	 */
	public int infoUpdateDaySum(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateDaySum(example);
	}

	/**
	 * 信息更新合计 周
	 * 
	 * @param info
	 * @return
	 */
	public int infoUpdateWeekSum(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateWeekSum(example);
	}

	/**
	 * 信息更新统计 周
	 * 
	 * @param info
	 * @return
	 */
	public List<Content> infoUpdateWeek(Content info) {
		ContentExample example = new ContentExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(info, criteria);
		return contentMapper.infoUpdateWeek(example);
	}

}
