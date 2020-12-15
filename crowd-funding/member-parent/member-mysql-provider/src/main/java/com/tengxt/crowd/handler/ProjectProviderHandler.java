package com.tengxt.crowd.handler;

import com.tengxt.crowd.entity.vo.ProjectVO;
import com.tengxt.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tengxt.util.ResultEntity;

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

}
