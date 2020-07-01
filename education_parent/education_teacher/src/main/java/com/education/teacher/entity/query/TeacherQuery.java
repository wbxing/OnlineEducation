package com.education.teacher.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "Teacher查询对象", description = "讲师查询对象封装")
@Data
public class TeacherQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private String level;

    @ApiModelProperty(value = "最早创建时间", example = "1900-01-01 00:00:00")
    private String earliest; // 注意，这里使用的是 String 类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "最晚创建时间", example = "1900-12-01 00:00:00")
    private String latest;
}

