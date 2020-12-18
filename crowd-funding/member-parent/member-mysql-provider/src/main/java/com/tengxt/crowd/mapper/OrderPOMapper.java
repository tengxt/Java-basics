package com.tengxt.crowd.mapper;

import com.tengxt.crowd.entity.po.OrderPO;
import com.tengxt.crowd.entity.po.OrderPOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderPOMapper {
    int countByExample(OrderPOExample example);

    int deleteByExample(OrderPOExample example);

    int insert(OrderPO record);

    int insertSelective(OrderPO record);

    List<OrderPO> selectByExample(OrderPOExample example);

    int updateByExampleSelective(@Param("record") OrderPO record, @Param("example") OrderPOExample example);

    int updateByExample(@Param("record") OrderPO record, @Param("example") OrderPOExample example);
}