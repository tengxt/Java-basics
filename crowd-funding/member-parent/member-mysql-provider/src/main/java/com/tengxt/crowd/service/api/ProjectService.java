package com.tengxt.crowd.service.api;

import com.tengxt.crowd.entity.vo.ProjectVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProjectService {

    void saveProject(@RequestBody ProjectVO projectVO, @RequestParam Integer memberId);
}
