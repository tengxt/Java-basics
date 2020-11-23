package com.tengxt.crowd.handler;

import com.tengxt.crowd.entity.po.MemberPO;
import com.tengxt.crowd.service.api.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tengxt.util.ResultEntity;

@RestController
public class MemberProviderHandler {
    private Logger logger = LoggerFactory.getLogger(MemberProviderHandler.class);

    @Autowired
    private MemberService memberService;

    @RequestMapping("/get/member/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {
        try {
            ResultEntity<MemberPO> loginAcctRemote = memberService.getMemberPOByLoginAcct(loginacct);
            return loginAcctRemote;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }
}
