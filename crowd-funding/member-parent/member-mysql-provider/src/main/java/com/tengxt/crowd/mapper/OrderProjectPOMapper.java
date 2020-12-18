package com.tengxt.crowd.mapper;

import com.tengxt.crowd.entity.po.OrderProjectPO;
import com.tengxt.crowd.entity.po.OrderProjectPOExample;
import com.tengxt.crowd.entity.vo.OrderProjectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderProjectPOMapper {
    int countByExample(OrderProjectPOExample example);

    int deleteByExample(OrderProjectPOExample example);

    int insert(OrderProjectPO record);

    int insertSelective(OrderProjectPO record);

    List<OrderProjectPO> selectByExample(OrderProjectPOExample example);

    int updateByExampleSelective(@Param("record") OrderProjectPO record, @Param("example") OrderProjectPOExample example);

    int updateByExample(@Param("record") OrderProjectPO record, @Param("example") OrderProjectPOExample example);

    OrderProjectVO selectOrderProjectVO(@Param("returnId") Integer returnId);
}