package com.nami.boot.mapper;

import java.util.List;

import com.nami.boot.entity.Assets;

public interface AssetsMapper {
	
	List<Assets> selectAllUser();
	
    int deleteByPrimaryKey(Integer id);

    int insert(Assets record);

    int insertSelective(Assets record);

    Assets selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Assets record);

    int updateByPrimaryKey(Assets record);
}