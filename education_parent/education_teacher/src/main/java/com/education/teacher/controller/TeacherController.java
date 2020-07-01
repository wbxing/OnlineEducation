package com.education.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.teacher.entity.Teacher;
import com.education.teacher.entity.query.TeacherQuery;
import com.education.teacher.exception.EduException;
import com.education.teacher.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wbxing
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/teacher")
@Api(description = "讲师管理")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("list")
    private Result getTeacherList() {
        try {
            List<Teacher> teachers = eduTeacherService.list(null);
            return Result.ok().data("teachers", teachers);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "所有讲师列表")
    @PostMapping("{page}/{limit}")
    private Result getTeacherByPage(@ApiParam(name = "page", value = "当前页", required = true)
                                    @PathVariable(value = "page") Integer page,
                                    @ApiParam(name = "limit", value = "每页数目", required = true)
                                    @PathVariable("limit") Integer limit,
                                    @ApiParam(name = "teacherQuery", value = "查询条件", required = false)
                                    @RequestBody TeacherQuery teacherQuery) {
        try {
            Page<Teacher> teacherPage = new Page<>(page, limit);
            eduTeacherService.pageQuery(teacherPage, teacherQuery);
            List<Teacher> records = teacherPage.getRecords();
            long total = teacherPage.getTotal();
            return Result.ok().data("total", total).data("records", records);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public Result removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        try {
            eduTeacherService.removeById(id);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public Result selectById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        Teacher teacher = eduTeacherService.getById(id);
        if (teacher == null) {
            throw new EduException(ResultCode.EDU_ID_ERROR, "没有此讲师信息");
        }
        try {
            return Result.ok().data("teacher", teacher);
        } catch (EduException e) {
            e.printStackTrace();
            return Result.error().code(e.getCode()).message(e.getMsg());
        }
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("update")
    public Result updateById(
            @ApiParam(name = "teacher", value = "修改后的讲师信息", required = true)
            @RequestBody Teacher teacher) {
        try {
            eduTeacherService.updateById(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("save")
    public Result save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {
        try {
            eduTeacherService.save(teacher);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error().message(e.getMessage());
        }
    }

}

