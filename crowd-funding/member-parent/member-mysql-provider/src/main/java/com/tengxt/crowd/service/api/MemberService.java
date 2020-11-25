package com.tengxt.crowd.service.api;

import com.tengxt.crowd.entity.po.MemberPO;
import org.springframework.web.bind.annotation.RequestParam;

public interface MemberService {

    MemberPO getMemberPOByLoginAcct(@RequestParam("loginacct") String loginacct);

    Integer saveMember(MemberPO memberPO);
}
