package com.education.teacher.service;

import com.education.teacher.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author wbxing
 * @since 2020-07-04
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 根据 EXCEL 文件导入课程分类信息
     * @param file 需要传递的 EXCEL 文件
     * @return 返回导入结果的集合信息
     */
    List<String> importExcel(MultipartFile file);
}
