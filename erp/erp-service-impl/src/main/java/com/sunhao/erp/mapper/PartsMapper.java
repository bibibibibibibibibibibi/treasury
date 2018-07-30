package com.sunhao.erp.mapper;


import com.sunhao.erp.entity.Parts;
import com.sunhao.erp.entity.PartsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PartsMapper {
    long countByExample(PartsExample example);

    int deleteByExample(PartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Parts record);

    int insertSelective(Parts record);

    List<Parts> selectByExample(PartsExample example);

    Parts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByExample(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByPrimaryKeySelective(Parts record);

    int updateByPrimaryKey(Parts record);

    List<Parts> findPartsWithType(@Param("partsName") String partsName, @Param("partsType") Integer partsType);
}