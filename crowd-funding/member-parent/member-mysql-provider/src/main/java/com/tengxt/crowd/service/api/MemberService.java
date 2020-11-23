package com.tengxt.crowd.service.api;

import com.tengxt.crowd.entity.po.MemberPO;
import org.springframework.web.bind.annotation.RequestParam;
import tengxt.util.ResultEntity;

public interface MemberService {

    ResultEntity<MemberPO> getMemberPOByLoginAcct(@RequestParam("loginacct") String loginacct);
}
