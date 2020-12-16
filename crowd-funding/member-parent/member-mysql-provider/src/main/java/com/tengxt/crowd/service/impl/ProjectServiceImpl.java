package com.tengxt.crowd.service.impl;

import com.tengxt.crowd.entity.po.MemberConfirmInfoPO;
import com.tengxt.crowd.entity.po.MemberLaunchInfoPO;
import com.tengxt.crowd.entity.po.ProjectPO;
import com.tengxt.crowd.entity.po.ReturnPO;
import com.tengxt.crowd.entity.vo.*;
import com.tengxt.crowd.mapper.*;
import com.tengxt.crowd.service.api.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private ReturnPOMapper returnPOMapper;

    @Autowired
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;


    @Override
    public List<PortalTypeVO> getPortalTypeVOList() {
        return projectPOMapper.selectPortalTypeVOList();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        // 一、保存ProjectPO对象
        // 1.初始化一个ProjectPO
        ProjectPO projectPO = new ProjectPO();
        // 2.利用工具方法，给ProjectPO赋值
        BeanUtils.copyProperties(projectVO, projectPO);
        // 3.给projectPO设置memberId
        projectPO.setMemberid(memberId);
        // 4.给projectPO设置项目创建时间
        String createDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createDate);
        // 5.设置status=0，表示项目即将开始
        projectPO.setStatus(0);
        // 6.向数据库保存ProjectPO
        // 为了在ProjectPO得到自增的主键，
        // 在mapper的xml文件中对应的insert标签增加了useGeneratedKeys="true" keyProperty="id"的配置
        projectPOMapper.insertSelective(projectPO);
        // 得到projectId
        Integer projectId = projectPO.getId();


        // 二、保存项目、分类关联关系信息
        // 1.得到TypeList
        List<Integer> typeIdList = projectVO.getTypeIdList();
        if (null == typeIdList || typeIdList.size() == 0) {
            throw new NullPointerException("获取分类关联关系信息为空");
        }
        // 2.执行保存操作
        projectPOMapper.saveTypeRelationship(projectId, typeIdList);


        // 三、保存项目、标签关联关系信息
        // 1. 得到TagIdList
        List<Integer> tagIdList = projectVO.getTagIdList();
        if (null == tagIdList || tagIdList.size() == 0) {
            throw new NullPointerException("获取标签关联关系信息为空");
        }
        // 2.执行保存操作
        projectPOMapper.saveTagRelationship(projectId, tagIdList);


        // 四、保存项目中详情图片路径信息
        // 1.得到detailPicturePathList
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        if (null == detailPicturePathList || detailPicturePathList.size() == 0) {
            throw new NullPointerException("获取详情图片路径信息为空");
        }
        // 2.执行保存操作
        projectItemPicPOMapper.insertPathList(projectId, detailPicturePathList);


        // 五、保存项目发起人信息
        // 1.得到发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        // 2.初始化MemberLaunchInfoPO
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        // 3.给MemberLaunchInfoPO赋值
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        // 4.设置MemberLaunchInfoPO的memberId
        memberLaunchInfoPO.setMemberid(memberId);
        // 5.保存发起人信息
        memberLaunchInfoPOMapper.insertSelective(memberLaunchInfoPO);


        // 六、保存项目回报信息
        // 1.得到项目汇报信息的List
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        if (null == returnVOList || returnVOList.size() == 0) {
            throw new NullPointerException("获取项目汇报信息为空");
        }
        // 2.初始化一个ReturnPO的list
        List<ReturnPO> returnPOList = new ArrayList<>();
        // 3.遍历给ReturnPO赋值 并存入List
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO = new ReturnPO();
            BeanUtils.copyProperties(returnVO, returnPO);
            returnPOList.add(returnPO);
        }
        // 4.将returnPOList存入数据库
        returnPOMapper.insertReturnPOList(projectId, returnPOList);


        // 七、保存项目确认信息
        // 1.得到MemberConfirmInfoVO
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        // 2.初始化MemberConfirmInfoPO对象
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        // 3.给MemberConfirmInfoPO赋值
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPO);
        // 4.给MemberConfirmInfoPO设置memberId
        memberConfirmInfoPO.setMemberid(memberId);
        // 将MemberConfirmInfoPO存入数据库
        memberConfirmInfoPOMapper.insertSelective(memberConfirmInfoPO);
    }
}
