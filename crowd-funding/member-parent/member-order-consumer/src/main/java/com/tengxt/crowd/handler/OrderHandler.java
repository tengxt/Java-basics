package com.tengxt.crowd.handler;

import com.tengxt.crowd.MySQLRemoteService;
import com.tengxt.crowd.entity.vo.AddressVO;
import com.tengxt.crowd.entity.vo.LoginMemberVO;
import com.tengxt.crowd.entity.vo.OrderProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tengxt.constant.CrowdConstant;
import tengxt.util.ResultEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    private final String ORDER_SESSION_KEY = "orderProjectVO";

    // 进入确认回报信息的页面
    @RequestMapping("/confirm/return/info/{returnId}")
    public String confirmReturnInfo(@PathVariable("returnId") Integer returnId, HttpSession session) {
        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVO(returnId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            session.setAttribute(ORDER_SESSION_KEY, resultEntity.getData());
        }
        return "order-confirm-return";
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String toConfirmOrderPage(@PathVariable("returnCount") Integer returnCount, HttpSession session) {
        // 从session域拿到orderProjectVO对象
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(ORDER_SESSION_KEY);
        // 给orderProjectVO设置回报的数量
        orderProjectVO.setReturnCount(returnCount);
        // 重新将orderProjectVO放回session域
        session.setAttribute(ORDER_SESSION_KEY, orderProjectVO);
        // 获取当前的用户的Address对象（即收货地址信息）
        // 1、先得到当前的用户id
        LoginMemberVO loginMember = (LoginMemberVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = loginMember.getId();
        // 2、通过用户id得到他的收货地址的List
        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressListByMemberIdRemote(memberId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            // 3、如果成功，则将收货地址放入session域
            List<AddressVO> addressVOList = resultEntity.getData();
            session.setAttribute("addressVOList", addressVOList);
        }
        // 进入确认订单页面
        return "order-confirm-order";
    }

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO, HttpSession session) {
        // 通过远程方法保存地址信息
        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);
        // 从session域得到当前的orderProjectVO
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute(ORDER_SESSION_KEY);
        // 得到当前的回报数量
        Integer returnCount = orderProjectVO.getReturnCount();
        // 再次重定向到确认订单的页面（附带回报数量）
        return "redirect:http://localhost/order/confirm/order/" + returnCount;
    }
}
