package tengxt.mvc.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tengxt.entity.Admin;
import tengxt.entity.ParamData;
import tengxt.entity.Student;
import tengxt.service.api.AdminService;
import tengxt.util.CrowdUtil;
import tengxt.util.ResultEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {

    private Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @Autowired
    private AdminService adminService;


    @ResponseBody
    @RequestMapping("/send/array/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
        for (Integer number : array) {
            logger.info("number one ==>" + number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array/two.html")
    public String testReceiveArrayTwo(ParamData paramData) {
        List<Integer> array = paramData.getArray();
        for (Integer number : array) {
            logger.info("number two ==>" + number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array/three.html")
    public String testReceiveArrayThree(@RequestBody List<Integer> array) {
        for (Integer number : array) {
            logger.info("number three ==>" + number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array/object.html")
    public String testReceiveArrayFour(@RequestBody Student student) {
        logger.info("Student ==>" + student.toString());
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/array/object.json")
    public ResultEntity<Student> testResultEntity(@RequestBody Student student,
                                                  HttpServletRequest request) {

        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        logger.info("judgeResult ==> " + judgeResult);

        logger.info("Student ==>" + student.toString());
        return ResultEntity.successWithData(student);
    }


    @RequestMapping("/test/ssm.html")
    public String testSSM(ModelMap modelMap, HttpServletRequest request) {

        boolean judgeResult = CrowdUtil.judgeRequestType(request);

        logger.info("judgeResult ==> " + judgeResult);

        List<Admin> adminList = adminService.queryAll();
        modelMap.addAttribute("adminList", adminList);

        System.out.println(10 / 0);
        return "target";
    }
}
