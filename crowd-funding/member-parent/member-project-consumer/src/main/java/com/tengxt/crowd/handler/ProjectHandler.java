package com.tengxt.crowd.handler;

import com.tengxt.crowd.MySQLRemoteService;
import com.tengxt.crowd.config.UploadFileConfig;
import com.tengxt.crowd.entity.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tengxt.constant.CrowdConstant;
import tengxt.util.ResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectHandler {
    private Logger logger = LoggerFactory.getLogger(ProjectHandler.class);

    @Autowired
    private UploadFileConfig uploadFileConfig;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/create/project/information")
    public String createProInformation(
            ProjectVO projectVO,
            MultipartFile headerPicture,
            List<MultipartFile> detailPictureList,
            HttpServletRequest request,
            ModelMap modelMap) {
        // 一、完成头图图片的上传
        // 判断headerPicture对象是否为空
        boolean headerPictureEmpty = headerPicture.isEmpty();
        if (headerPictureEmpty) {
            // 头图为空，存入提示信息，且返回原本的页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";
        }
        // 头图不为空 进行上传操作
        String singleFile = fileUploadBySingle(headerPicture, request);
        if (StringUtils.isEmpty(singleFile)) {
            // 上传失败
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        } else {
            // 存入ProjectVO对象
            projectVO.setHeaderPicturePath(singleFile);
        }
        // 二、完成详情图片的上传
        // 创建用于存放详情图片的路径的List对象
        List<String> detailPicturePathList = new ArrayList<>();
        // 判断详情图片是否为空
        if (detailPictureList == null || detailPictureList.size() == 0) {
            // 详情图片为空，加入提示信息，返回原本页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-launch";
        }
        // 详情图片不为空 遍历List
        for (MultipartFile detailPicture : detailPictureList) {
            // 判断当前MultipartFile是否有效
            if (detailPicture.isEmpty()) {
                // 当前图片为空，也返回原本的页面
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }
            // 不为空，开始存放详情图片
            String detailPictureFile = fileUploadBySingle(detailPicture, request);
            if (StringUtils.isEmpty(detailPictureFile)) {
                // 上传失败
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
            // 存入ProjectVO对象
            detailPicturePathList.add(detailPictureFile);
        }
        // 将detailPicturePathList存入ProjectVO对象
        projectVO.setDetailPicturePathList(detailPicturePathList);

        // 后续操作
        // 将ProjectVO对象放入session域
        request.getSession().setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

        // 进入下一个收集回报信息的页面
        return "redirect:http://localhost/project/return/info/page.html";
    }

    // 回报页面上传图片时触发的ajax请求对应的handler方法
    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile returnPicture, HttpServletRequest request) throws IOException {
        // 判断是否是有效上传
        boolean pictureIsEmpty = returnPicture.isEmpty();
        if (pictureIsEmpty) {
            // 如果上传文件为空
            return ResultEntity.failed(CrowdConstant.MESSAGE_RETURN_PIC_EMPTY);
        }

        String singleFile = fileUploadBySingle(returnPicture, request);
        if (StringUtils.isEmpty(singleFile)) {
            // 上传失败
            return ResultEntity.failed(CrowdConstant.MESSAGE_RETURN_PIC_EMPTY_FAILED);
        }

        // 返回上传结果
        return ResultEntity.successWithData(singleFile);
    }

    // 回报页面保存回报信息的ajax请求对应的方法
    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {
        try {
            // 从session域取出ProjectVO对象
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

            // 判断ProjectVO是否回null
            if (projectVO == null) {
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }

            // ProjectVO不为null
            // 取出projectVO中的returnVOList
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();

            // 判断取出的list是否为空或长度为0
            if (returnVOList == null || returnVOList.size() == 0) {
                // 初始化returnVOList
                returnVOList = new ArrayList<>();
                // 存入projectVO
                projectVO.setReturnVOList(returnVOList);
            }
            // 向returnVOList中存放当前接收的returnVO
            returnVOList.add(returnVO);

            // 重新将ProjectVO存入session域
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT, projectVO);

            // 全部操作正常完成，返回成功的ResultEntity
            return ResultEntity.successWithoutData();

        } catch (Exception e) {
            e.printStackTrace();
            // 出现异常返回failed，带上异常信息
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/create/confirm.html")
    public String saveConfirm(MemberConfirmInfoVO memberConfirmInfoVO, HttpSession session, ModelMap modelMap) {
        // 从session域取出ProjectVO对象
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        // 判断ProjectVO是否回null
        if (projectVO == null) {
            // 这里不多做处理了，就直接抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }
        // ProjectVO正常，开始向其中存放MemberConfirmInfo
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        // 从session域中读取当前登录的用户
        LoginMemberVO loginMember = (LoginMemberVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = loginMember.getId();

        // 调用远程方法保存ProjectVO对象和当前登录的用户的id
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectRemote(projectVO, memberId);
        String result = saveResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)) {
            // 保存出错，返回确认的界面，并且携带错误的消息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());
            return "project-confirm";
        }

        // 保存正常完成，删除session中临时存放的ProjectVO
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);

        // 进入成功页面
        return "redirect:http://localhost/project/create/success.html";
    }


    @RequestMapping("/show/project/detail/{projectId}")
    public String getDetailProject(
            @PathVariable("projectId") Integer projectId,
            ModelMap modelMap) {
        ResultEntity<DetailProjectVO> resultEntity = mySQLRemoteService.getDetailProjectVORemote(projectId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            DetailProjectVO detailProjectVO = resultEntity.getData();
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_DETAIL_PROJECT, detailProjectVO);
        }
        return "project-show-detail";
    }


    /**
     * 上传单个文件
     *
     * @param file
     * @return
     */
    public String fileUploadBySingle(MultipartFile file, HttpServletRequest request) {
        // 上传文件保存的本地目录
        String uploadFileLocation = uploadFileConfig.getLocation();
        // //请求 url 中的资源映射  /uploadFile/**
        String resourceHandler = uploadFileConfig.getResourceHandler();
        // 判断文件是否有内容
        if (file.isEmpty()) {
            logger.debug("文件：" + file + "中无任何内容！");
        }
        // basePath拼接完成后，形如：http://127.0.0.1:9005/fileServer
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String fileName = file.getOriginalFilename();
        // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
        int index = fileName.lastIndexOf("\\");
        if (index > 0) {
            fileName = fileName.substring(index + 1);
        }
        // 判断单个文件大于2M
        long fileSize = file.getSize();
        if (fileSize > 1024 * 1024 * 2) {
            logger.debug("文件：" + file + " 的大小为(单位字节)： " + fileSize);
        }
        // 当文件有后缀名时
        if (fileName.indexOf(".") >= 0) {
            // split()中放正则表达式; 转义字符"\\."代表 "."
            String[] fileNameSplitArray = fileName.split("\\.");
            // 加上random戳,防止附件重名覆盖原文件
            fileName = fileNameSplitArray[0] + (int) (Math.random() * 100000) + "." + fileNameSplitArray[1];
        }
        // 当文件无后缀名时(如C盘下的hosts文件就没有后缀名)
        if (fileName.indexOf(".") < 0) {
            // 加上random戳,防止附件重名覆盖原文件
            fileName = fileName + (int) (Math.random() * 100000);
        }
        String fileServerPath = basePath + resourceHandler.substring(0, resourceHandler.lastIndexOf("/") + 1) + fileName;

        File dest = new File(uploadFileLocation, fileName);
        // 如果该文件的上级文件夹不存在，则创建该文件的上级文件夹及其祖辈级文件夹;
        if (!dest.getParentFile().exists()) {
            boolean mkdirs = dest.getParentFile().mkdirs();
            if (mkdirs) {
                logger.info("目录：" + dest + " 创建成功...");
            }
        }
        try {
            // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
            file.transferTo(dest);
            logger.info("文件的全路径 >>>>>>> " + dest.getPath().toString() + " >>>>>>> 上传成功...");
        } catch (IllegalStateException e) {
            logger.error("fileUploadBySingle >>>>" + e.getMessage());
        } catch (IOException e) {
            logger.error("fileUploadBySingle >>>>" + e.getMessage());
        }
        return fileServerPath;
    }
}

