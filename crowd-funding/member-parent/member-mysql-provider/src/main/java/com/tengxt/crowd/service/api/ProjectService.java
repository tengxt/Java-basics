package com.tengxt.crowd.service.api;

import com.tengxt.crowd.entity.vo.DetailProjectVO;
import com.tengxt.crowd.entity.vo.PortalTypeVO;
import com.tengxt.crowd.entity.vo.ProjectVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProjectService {

    void saveProject(@RequestBody ProjectVO projectVO, @RequestParam Integer memberId);

    List<PortalTypeVO> getPortalTypeVOList();

    DetailProjectVO getDetailProjectVO(Integer projectId);
}
