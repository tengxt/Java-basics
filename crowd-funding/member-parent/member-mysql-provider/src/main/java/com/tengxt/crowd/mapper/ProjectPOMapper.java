package com.tengxt.crowd.mapper;

import com.tengxt.crowd.entity.po.ProjectPO;
import com.tengxt.crowd.entity.po.ProjectPOExample;
import com.tengxt.crowd.entity.vo.DetailProjectVO;
import com.tengxt.crowd.entity.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    void saveTypeRelationship(@Param("projectId") Integer projectId, @Param("typeIdList") List<Integer> typeIdList);

    void saveTagRelationship(@Param("projectId")  Integer projectId, @Param("tagIdList") List<Integer> tagIdList);

    List<PortalTypeVO> selectPortalTypeVOList();

    DetailProjectVO selectDetailProjectVO(Integer projectId);
}