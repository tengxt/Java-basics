package com.tengxt.crowd.mapper;

import com.tengxt.crowd.entity.po.MemberPO;
import com.tengxt.crowd.entity.po.MemberPOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberPOMapper {
    int countByExample(MemberPOExample example);

    int deleteByExample(MemberPOExample example);

    int insert(MemberPO record);

    int insertSelective(MemberPO record);

    List<MemberPO> selectByExample(MemberPOExample example);

    int updateByExampleSelective(@Param("record") MemberPO record, @Param("example") MemberPOExample example);

    int updateByExample(@Param("record") MemberPO record, @Param("example") MemberPOExample example);
}