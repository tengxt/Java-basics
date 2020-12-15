package com.tengxt.crowd.mapper;

import com.tengxt.crowd.entity.po.ProjectItemPicPO;
import com.tengxt.crowd.entity.po.ProjectItemPicPOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectItemPicPOMapper {
    int countByExample(ProjectItemPicPOExample example);

    int deleteByExample(ProjectItemPicPOExample example);

    int insert(ProjectItemPicPO record);

    int insertSelective(ProjectItemPicPO record);

    List<ProjectItemPicPO> selectByExample(ProjectItemPicPOExample example);

    int updateByExampleSelective(@Param("record") ProjectItemPicPO record, @Param("example") ProjectItemPicPOExample example);

    int updateByExample(@Param("record") ProjectItemPicPO record, @Param("example") ProjectItemPicPOExample example);

    void insertPathList(@Param("projectId") Integer projectId,
                        @Param("detailPicturePathList") List<String> detailPicturePathList);
}