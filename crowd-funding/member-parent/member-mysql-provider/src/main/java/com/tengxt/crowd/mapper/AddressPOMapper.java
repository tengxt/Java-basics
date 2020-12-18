package com.tengxt.crowd.mapper;

import com.tengxt.crowd.entity.po.AddressPO;
import com.tengxt.crowd.entity.po.AddressPOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressPOMapper {
    int countByExample(AddressPOExample example);

    int deleteByExample(AddressPOExample example);

    int insert(AddressPO record);

    int insertSelective(AddressPO record);

    List<AddressPO> selectByExample(AddressPOExample example);

    int updateByExampleSelective(@Param("record") AddressPO record, @Param("example") AddressPOExample example);

    int updateByExample(@Param("record") AddressPO record, @Param("example") AddressPOExample example);
}