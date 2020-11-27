package com.tengxt.crowd.handler;

import com.tengxt.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectProviderHandler {

    @Autowired
    private ProjectService projectService;

}
