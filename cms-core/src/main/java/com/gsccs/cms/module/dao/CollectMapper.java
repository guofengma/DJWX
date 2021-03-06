package com.gsccs.cms.module.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gsccs.cms.core.model.Content;
import com.gsccs.cms.module.model.Collect;
import com.gsccs.cms.module.model.CollectExample;

public interface CollectMapper {

	int countByExample(CollectExample example);

	int deleteByExample(CollectExample example);

	int deleteByPrimaryKey(String id);

	int insert(Collect record);

	List<Collect> selectByExample(CollectExample example);

	List<Collect> selectPageByExample(CollectExample example);

	Collect selectByPrimaryKey(String id);

	int updateByExampleSelective(@Param("record") Collect record,
			@Param("example") CollectExample example);

	int updateByExample(@Param("record") Collect record,
			@Param("example") CollectExample example);

	int updateByPrimaryKeySelective(Collect record);

	int updateByPrimaryKey(Collect record);
}