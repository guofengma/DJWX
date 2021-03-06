package com.gsccs.cms.member.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gsccs.cms.bass.utils.DateUtil;
import com.gsccs.cms.member.dao.CreditlogMapper;
import com.gsccs.cms.member.model.Creditlog;
import com.gsccs.cms.member.model.CreditlogExample;
import com.gsccs.cms.member.model.CreditlogExample.Criteria;
import com.gsccs.cms.member.service.CreditlogService;

/**
 * 积分日志相关服务
 * 
 * @author x.d zhang
 * @version 1.0
 */
@Service
public class CreditlogServiceImpl implements CreditlogService{

	@Resource
	private CreditlogMapper creditlogMapper;
	

	/**
	 * 分页查询
	 */
	public List<Creditlog> find(Creditlog Creditlog,String order,int currPage,int pageSize){
		CreditlogExample example=new CreditlogExample();
		Criteria criteria=example.createCriteria();
		proSearchParam(Creditlog, criteria);
		if (order!=null && order.trim().length()>0) {
			example.setOrderByClause(order);
		}
		example.setCurrPage(currPage);
		example.setPageSize(pageSize);
		return creditlogMapper.selectPageByExample(example);
	}
	/**
	 * 查询
	 */
	public List<Creditlog> find(Creditlog Creditlog,String order){
		CreditlogExample example=new CreditlogExample();
		Criteria criteria=example.createCriteria();
		proSearchParam(Creditlog, criteria);
		if (order!=null && order.trim().length()>0) {
			example.setOrderByClause(order);
		}
		return creditlogMapper.selectByExample(example);
	}
	/**
	 * 统计
	 * @param info
	 * @return
	 */
	public int count(Creditlog creditlog){
		CreditlogExample example=new CreditlogExample();
		Criteria criteria=example.createCriteria();
		proSearchParam(creditlog, criteria);
		return creditlogMapper.countByExample(example);
	}

	/**
	 * 处理查询条件
	 * @param info
	 * @param criteria
	 */
	public void proSearchParam(Creditlog Creditlog,Criteria criteria){
		if (Creditlog!=null ) {
			if (Creditlog.getCreditruleid()!=null && Creditlog.getCreditruleid().trim().length()>0) {
				criteria.andCreditruleidEqualTo(Creditlog.getCreditruleid().trim());
			}
			if (Creditlog.getMemberid()!=null && Creditlog.getMemberid().trim().length()>0) {
				criteria.andMemberidEqualTo(Creditlog.getMemberid().trim());
			}
			if (Creditlog.getMembername()!=null && Creditlog.getMembername().trim().length()>0) {
				criteria.andMembernameLike("%"+Creditlog.getMembername().trim()+"%");
			}
			if (Creditlog.getCredittimeToday()!=null) {
				criteria.andCredittimeBetween(
						DateUtil.parse(DateUtil.format(Creditlog.getCredittimeToday(), "yyyy-MM-dd")+" 00:00:00", "yyyy-MM-dd HH:mm:ss"),
						DateUtil.parse(DateUtil.format(Creditlog.getCredittimeToday(), "yyyy-MM-dd")+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			if (Creditlog.getCredittimeGreater()!=null) {
				criteria.andCredittimeGreaterThan(Creditlog.getCredittimeGreater());
			}
			if (Creditlog.getType()!=null) {
				criteria.andTypeEqualTo(Creditlog.getType());
			}
		}
	}

	/**
	 * 根据id查询
	 * @param id
	 * @param cache
	 * @return
	 */
	public Creditlog findById(String id){
		return creditlogMapper.selectByPrimaryKey(id);
	}
	/**
	 * 更新
	 * @param question
	 */
	public void update(Creditlog Creditlog){
		creditlogMapper.updateByPrimaryKeySelective(Creditlog);
	}
	/**
	 * 添加
	 * @param question
	 * @return
	 */
	public String add(Creditlog Creditlog){
		Creditlog.setId(UUID.randomUUID().toString());
		creditlogMapper.insert(Creditlog);
		return Creditlog.getId();
	}
	/**
	 * 删除 
	 * @param id
	 */
	public void del(String id){
		creditlogMapper.deleteByPrimaryKey(id);
	}

	public CreditlogMapper getCreditlogMapper() {
		return creditlogMapper;
	}

	public void setCreditlogMapper(CreditlogMapper creditlogMapper) {
		this.creditlogMapper = creditlogMapper;
	}
}
