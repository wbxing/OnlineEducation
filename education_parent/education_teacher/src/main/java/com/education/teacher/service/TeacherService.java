package com.education.teacher.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.teacher.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.education.teacher.entity.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author wbxing
 * @since 2020-06-24
 */
public interface TeacherService extends IService<Teacher> {

    /**
     *  根据多个条件查询讲师列表
     * @param pageParam 教师分页对象
     * @param teacherQuery 查询条件
     */
    void pageQuery(Page<Teacher> pageParam, TeacherQuery teacherQuery);

}
