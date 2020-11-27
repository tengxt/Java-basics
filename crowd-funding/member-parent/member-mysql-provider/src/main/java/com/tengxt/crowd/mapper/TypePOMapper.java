package com.tengxt.crowd.mapper;

import com.tengxt.crowd.entity.po.TypePO;
import com.tengxt.crowd.entity.po.TypePOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypePOMapper {
    int countByExample(TypePOExample example);

    int deleteByExample(TypePOExample example);

    int insert(TypePO record);

    int insertSelective(TypePO record);

    List<TypePO> selectByExample(TypePOExample example);

    int updateByExampleSelective(@Param("record") TypePO record, @Param("example") TypePOExample example);

    int updateByExample(@Param("record") TypePO record, @Param("example") TypePOExample example);
}