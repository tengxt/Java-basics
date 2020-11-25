package com.tengxt.crowd.handler;

import com.tengxt.crowd.entity.po.MemberPO;
import com.tengxt.crowd.service.api.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tengxt.constant.CrowdConstant;
import tengxt.util.ResultEntity;

@RestController
public class MemberProviderHandler {
    private Logger logger = LoggerFactory.getLogger(MemberProviderHandler.class);

    @Autowired
    private MemberService memberService;

    @RequestMapping("/get/member/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {
        try {
            MemberPO loginAcctRemote = memberService.getMemberPOByLoginAcct(loginacct);
            if (null == loginAcctRemote) {
                return ResultEntity.failed(null);
            }
            return ResultEntity.successWithData(loginAcctRemote);
        } catch (Exception e) {
            logger.debug("getMemberPOByLoginAcctRemote ==> " + e.getMessage());
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/save/member/remote")
    public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO) {
        try {
            memberService.saveMember(memberPO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            logger.debug("saveMemberRemote ==> " + e.getMessage());
            if (e instanceof DuplicateKeyException) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
            }
            return ResultEntity.failed(e.getMessage());
        }
    }
}
