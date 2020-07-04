package com.education.teacher.controller;


import com.education.common.Result;
import com.education.teacher.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author wbxing
 * @since 2020-07-04
 */
@RestController
@Api(description = "课程管理")
@RequestMapping("/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "导入课程信息")
    @PostMapping("import")
    public Result importSubject(@ApiParam(name = "file", value = "课程文件", required = true) MultipartFile file) {

        List<String> messages = subjectService.importExcel(file);

        if (messages.size() == 0) {
            return Result.ok();
        } else {
            return Result.error().data("messages", messages);
        }
    }

}

