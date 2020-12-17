package com.tengxt.crowd.handler;

import com.tengxt.crowd.entity.vo.DetailProjectVO;
import com.tengxt.crowd.entity.vo.PortalTypeVO;
import com.tengxt.crowd.entity.vo.ProjectVO;
import com.tengxt.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tengxt.util.ResultEntity;

import java.util.List;

@RestController
public class ProjectProviderHandler {

    @Autowired
    private ProjectService projectService;

    // 将ProjectVO中的数据存入各个数据库
    @RequestMapping("/save/project/remote")
    public ResultEntity<String> saveProjectRemote(@RequestBody ProjectVO projectVO,
                                                  @RequestParam("memberId") Integer memberId) {
        // 调用本地service进行保存
        try {
            projectService.saveProject(projectVO, memberId);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote() {
        try {
            List<PortalTypeVO> portalTypeVOList = projectService.getPortalTypeVOList();
            return ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    // 传入的 /{projectId} 需要在传参处使用 @PathVariable("projectId")来接收
    @RequestMapping("/get/detail/project/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(
            @PathVariable("projectId") Integer projectId) {
        return ResultEntity.successWithData(projectService.getDetailProjectVO(projectId));
    }

}
