package com.tengxt.crowd.service.api;

import com.tengxt.crowd.entity.vo.AddressVO;
import com.tengxt.crowd.entity.vo.OrderProjectVO;
import com.tengxt.crowd.entity.vo.OrderVO;

import java.util.List;

public interface OrderService {

    OrderProjectVO getOrderProjectVO(Integer returnId);

    List<AddressVO> getAddressListVOByMemberId(Integer memberId);

    void saveAddressPO(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);

}
