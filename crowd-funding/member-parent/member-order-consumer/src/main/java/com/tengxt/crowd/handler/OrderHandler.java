package com.tengxt.crowd.handler;

import com.tengxt.crowd.MySQLRemoteService;
import com.tengxt.crowd.entity.vo.OrderProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tengxt.util.ResultEntity;

import javax.servlet.http.HttpSession;

@Controller
public class OrderHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    // 进入确认回报信息的页面
    @RequestMapping("/confirm/return/info/{returnId}")
    public String confirmReturnInfo(@PathVariable("returnId") Integer returnId, HttpSession session) {
        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVO(returnId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            session.setAttribute("orderProjectVO", resultEntity.getData());
        }
        return "order-confirm-return";
    }
}
