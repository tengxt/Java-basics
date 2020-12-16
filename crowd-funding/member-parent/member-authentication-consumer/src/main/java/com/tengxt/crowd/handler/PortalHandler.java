package com.tengxt.crowd.handler;

import com.tengxt.crowd.MySQLRemoteService;
import com.tengxt.crowd.entity.vo.PortalTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import tengxt.constant.CrowdConstant;
import tengxt.util.ResultEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PortalHandler {


    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    // 首页，直接访问，而不用加额外的路径
    @RequestMapping("/")
    public String showPortalPage(ModelMap modelMap) {
        // 调用MySQLRemoteService提供的方法查询首页要显示的数据
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPortalTypeProjectDataRemote();
        // 如果操作成功，将得到的list加入请求域
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            List<PortalTypeVO> portalTypeVOList = resultEntity.getData();
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_TYPE_LIST, portalTypeVOList);
        }

        return "portal";
    }

    // 退出登录
    @RequestMapping("/auth/do/member/logout.html")
    public String doLogout(HttpSession session) {
        // 清除session域数据
        session.invalidate();

        // 重定向到首页
        return "redirect:http://localhost:9004/";
    }
}
