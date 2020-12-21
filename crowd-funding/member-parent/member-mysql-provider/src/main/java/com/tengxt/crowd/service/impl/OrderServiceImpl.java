package com.tengxt.crowd.service.impl;

import com.tengxt.crowd.entity.po.AddressPO;
import com.tengxt.crowd.entity.po.AddressPOExample;
import com.tengxt.crowd.entity.po.OrderPO;
import com.tengxt.crowd.entity.po.OrderProjectPO;
import com.tengxt.crowd.entity.vo.AddressVO;
import com.tengxt.crowd.entity.vo.OrderProjectVO;
import com.tengxt.crowd.entity.vo.OrderVO;
import com.tengxt.crowd.mapper.AddressPOMapper;
import com.tengxt.crowd.mapper.OrderPOMapper;
import com.tengxt.crowd.mapper.OrderProjectPOMapper;
import com.tengxt.crowd.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;

    @Autowired
    private OrderPOMapper orderPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer returnId) {
        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressListVOByMemberId(Integer memberId) {
        AddressPOExample example = new AddressPOExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        List<AddressPO> addressPOList = addressPOMapper.selectByExample(example);
        List<AddressVO> addressVOList = new ArrayList<>();
        if (null == addressPOList || addressPOList.size() == 0) {
            throw new NullPointerException("地址数据为空");
        }
        for (AddressPO addressPO : addressPOList) {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO,addressVO);
            addressVOList.add(addressVO);
        }
        return addressVOList;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveOrder(OrderVO orderVO) {
        // 创建OrderPO对象
        OrderPO orderPO = new OrderPO();
        // 从传入的OrderVO给OrderPO赋值
        BeanUtils.copyProperties(orderVO,orderPO);
        // 将OrderPO存入数据库
        orderPOMapper.insert(orderPO);
        // 得到存入后自增产生的order id
        Integer orderId = orderPO.getId();
        // 得到orderProjectVO
        OrderProjectVO orderProjectVO = orderVO.getOrderProjectVO();
        // 创建OrderProjectPO对象
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        // 赋值
        BeanUtils.copyProperties(orderProjectVO,orderProjectPO);
        // 给orderProjectPO设置orderId
        orderProjectPO.setOrderId(orderId);
        // 存入数据库
        orderProjectPOMapper.insert(orderProjectPO);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public void saveAddressPO(AddressVO addressVO) {
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(addressVO,addressPO);
        addressPOMapper.insert(addressPO);
    }
}
