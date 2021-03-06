package com.gsccs.cms.member.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.gsccs.cms.member.dao.MemberMapper;
import com.gsccs.cms.member.model.Member;
import com.gsccs.cms.member.model.MemberExample;
import com.gsccs.cms.member.model.MemberExample.Criteria;
import com.gsccs.cms.member.service.CreditruleService;
import com.gsccs.cms.member.service.MemberService;
import com.gsccs.cms.member.service.MembergroupService;

/**
 * 会员相关服务
 * 
 * @author x.d zhang
 * @version 1.0
 * 
 */
@Service
public class MemberServiceImpl implements MemberService {

	@Resource
	private MemberMapper memberMapper;
	@Resource
	private MembergroupService membergroupService;
	@Resource
	private CreditruleService creditruleService;

	/**
	 * 分页查询
	 */
	public List<Member> find(Member member, String order, int currPage,
			int pageSize) {
		MemberExample example = new MemberExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(member, criteria);
		if (order != null && order.trim().length() > 0) {
			example.setOrderByClause(order);
		}
		example.setCurrPage(currPage);
		example.setPageSize(pageSize);
		return memberMapper.selectPageByExample(example);
	}

	@Override
	public List<Member> find(String wxappid) {
		if (StringUtils.isEmpty(wxappid)) {
			return null;
		}
		MemberExample example = new MemberExample();
		Criteria criteria = example.createCriteria();
		criteria.andWxappidEqualTo(wxappid);
		return memberMapper.selectByExample(example);
	}

	/**
	 * 查询
	 */
	public List<Member> find(Member member, String order) {
		MemberExample example = new MemberExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(member, criteria);
		if (order != null && order.trim().length() > 0) {
			example.setOrderByClause(order);
		}
		return memberMapper.selectByExample(example);
	}

	/**
	 * 统计
	 * 
	 * @param info
	 * @return
	 */
	public int count(Member member) {
		MemberExample example = new MemberExample();
		Criteria criteria = example.createCriteria();
		proSearchParam(member, criteria);
		return memberMapper.countByExample(example);
	}

	/**
	 * 处理查询条件
	 * 
	 * @param info
	 * @param criteria
	 */
	public void proSearchParam(Member member, Criteria criteria) {
		if (member != null) {
			if (member.getName() != null
					&& member.getName().trim().length() > 0) {
				criteria.andMNameLike("%" + member.getName().trim() + "%");
			}

			if (member.getWxappid() != null
					&& member.getWxappid().trim().length() > 0) {
				criteria.andWxappidEqualTo(member.getWxappid());
			}
		}
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @param cache
	 * @return
	 */
	public Member findById(String id) {
		return memberMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新
	 * 
	 * @param question
	 */
	public void update(Member member) {
		memberMapper.updateByPrimaryKeySelective(member);
	}

	/**
	 * 添加
	 * 
	 * @param question
	 * @return
	 */
	public String add(Member member) {
		if (null == member || StringUtils.isEmpty(member.getId())) {
			return null;
		}

		// 是否已存在
		Member dbmember = findById(member.getId());
		if (null != dbmember) {
			member.setRealid(member.getRealid());
			memberMapper.updateByPrimaryKey(member);
		} else {
			memberMapper.insert(member);
		}
		return member.getId();
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void del(String id) {
		memberMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 判断会员是否存在
	 * 
	 * @param member
	 * @return
	 */
	public boolean have(Member member) {
		MemberExample example = new MemberExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(member.getId());
		return memberMapper.countByExample(example) > 0;
	}

	/**
	 * 按登录名查询
	 * 
	 * @param member
	 * @return
	 */
	public Member findByWxOpenid(Member member) {
		MemberExample example = new MemberExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(member.getId().trim());
		List<Member> list = memberMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 启用/禁用
	 * 
	 * @param id
	 * @param isok
	 */
	public void isok(String id, String isok) {
		Member member = new Member();
		member.setId(id);
		memberMapper.updateByPrimaryKeySelective(member);
	}

	@Override
	public List<Member> findAuthedList(String wxappid, String unitid) {
		return memberMapper.selectAuthedList(wxappid, unitid);
	}

}
