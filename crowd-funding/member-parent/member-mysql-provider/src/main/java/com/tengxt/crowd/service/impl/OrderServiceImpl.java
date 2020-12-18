package com.tengxt.crowd.service.impl;

import com.tengxt.crowd.entity.vo.OrderProjectVO;
import com.tengxt.crowd.mapper.OrderProjectPOMapper;
import com.tengxt.crowd.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer returnId) {
        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }
}
