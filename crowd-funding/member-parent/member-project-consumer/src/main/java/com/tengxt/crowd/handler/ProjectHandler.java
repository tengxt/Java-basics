package com.tengxt.crowd.handler;

import com.tengxt.crowd.config.UploadFileConfig;
import com.tengxt.crowd.entity.vo.ProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import tengxt.constant.CrowdConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectHandler {
    private Logger logger = LoggerFactory.getLogger(ProjectHandler.class);

    @Autowired
    private UploadFileConfig uploadFileConfig;

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
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
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
        return dest.getPath().toString();
    }
}

