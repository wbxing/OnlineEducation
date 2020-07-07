package com.education.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.teacher.entity.Subject;
import com.education.teacher.mapper.SubjectMapper;
import com.education.teacher.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author wbxing
 * @since 2020-07-04
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    // 日志输出
    private final Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Override
    public List<String> importExcel(MultipartFile file) {
        InputStream in = null;
        // 存储错误信息的集合
        List<String> message = new ArrayList<>();
        try {
            // 获取文件流
            in = file.getInputStream();
            // 创建 workBook
            Workbook workbook = new HSSFWorkbook(in);
            // 获取 Sheet
            Sheet sheet = workbook.getSheetAt(0);
            // 获取行数
            int lastRowNum = sheet.getLastRowNum();
            logger.debug("获取行数：" + lastRowNum);
            if (lastRowNum <= 1) {
                message.add("请填写数据");
                return message;
            }
            // 遍历行
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                Row rowData = sheet.getRow(rowNum);
                // 根据每一行获取列数据
                // 获取第一列
                // 第一列是一级分类
                Cell cellOne = rowData.getCell(0);
                // 判断列是否存在，存在就根据 cell 获取第一列里面内容
                if (cellOne == null) {
                    message.add("第 " + rowNum + " 行第 1 列数据为空");
                    // 跳到下一行进行操作
                    continue;
                }
                String cellOneValue = cellOne.getStringCellValue();
                if (StringUtils.isEmpty(cellOneValue)) {
                    message.add("第 " + rowNum + " 行第 1 列数据为空");
                    // 跳到下一行进行操作
                    continue;
                }
                // cellOneValue 是一级分类名称
                // 判断添加的一级分类名称在数据库表里面是否有相同的，如果没有添加
                Subject subject = this.existOneSubject(cellOneValue);
                // 为了后面添加二级分类，一级分类id
                String pid;
                // 表没有相同的一级分类
                if (subject == null) {
                    // 添加到数据库里面
                    Subject subjectOne = new Subject();
                    subjectOne.setTitle(cellOneValue);
                    subjectOne.setParentId("0");
                    baseMapper.insert(subjectOne);
                    // 添加一级分类之后，获取添加之后id值
                    pid = subjectOne.getId();
                } else {
                    // 有相同的一级分类
                    pid = subject.getId();
                }
                // 获取第二列
                // 第二列是二级分类
                Cell cellTwo = rowData.getCell(1);
                if (cellTwo == null) {
                    message.add("第 " + rowNum + " 行第 2 列数据为空");
                    continue;
                }
                // 根据 cell 获取第二列里面内容
                String cellTwoValue = cellTwo.getStringCellValue();
                if (StringUtils.isEmpty(cellTwoValue)) {
                    message.add("第 " + rowNum + " 行第 2 列数据为空");
                    continue;
                }
                // cellTwoValue 是二级分类名称
                Subject subjectTwo = this.existTwoSubject(cellTwoValue, pid);
                if (subjectTwo == null) {
                    //没有相同的二级分类，实现二级分类添加
                    Subject newSubject = new Subject();
                    newSubject.setTitle(cellTwoValue);
                    newSubject.setParentId(pid);
                    baseMapper.insert(newSubject);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    /**
     * 判断二级分类名称是否有相同的
     *
     * @param name 类别名称
     * @param pid  对应一级分类的 id
     * @return 返回判断结果
     */
    private Subject existTwoSubject(String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);

        return baseMapper.selectOne(wrapper);
    }

    /**
     * 判断一级分类名称是否相同
     *
     * @param name 类别名称
     * @return 返回判断结果
     */
    private Subject existOneSubject(String name) {
        // 查询数据库判断
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return baseMapper.selectOne(wrapper);
    }

}
