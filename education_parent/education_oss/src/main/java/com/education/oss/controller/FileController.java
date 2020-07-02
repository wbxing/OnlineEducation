package com.education.oss.controller;

import com.education.common.Result;
import com.education.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(description = "阿里云文件管理")
@CrossOrigin // 跨域
@RestController
@RequestMapping("oss")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传
     *
     * @param file 需要上传的文件
     * @return 图片地址
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("file/upload")
    public Result upload(@ApiParam(name = "file", value = "文件", required = true)
                         @RequestParam("file") MultipartFile file) {

        String uploadUrl = fileService.upload(file);
        // 返回 Result 对象
        return Result.ok().message("文件上传成功").data("url", uploadUrl);
    }
}

