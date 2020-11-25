package com.tengxt.crowd.service.impl;

import com.tengxt.crowd.entity.po.MemberPO;
import com.tengxt.crowd.entity.po.MemberPOExample;
import com.tengxt.crowd.mapper.MemberPOMapper;
import com.tengxt.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tengxt.constant.CrowdConstant;
import tengxt.util.ResultEntity;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPOMapper memberPOMapper;

    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        MemberPOExample example = new MemberPOExample();
        MemberPOExample.Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(loginacct);
        List<MemberPO> memberPOList = memberPOMapper.selectByExample(example);
        // 未查询到该用户
        if (memberPOList == null || memberPOList.size() == 0) {
            return null;
        }
        MemberPO memberPO = memberPOList.get(0);
        return memberPO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Integer saveMember(MemberPO memberPO) {
        return memberPOMapper.insertSelective(memberPO);
    }
}
