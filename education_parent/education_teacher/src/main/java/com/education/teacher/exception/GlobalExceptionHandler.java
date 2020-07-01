package com.education.teacher.exception;

import com.baomidou.mybatisplus.extension.api.R;
import com.education.common.Result;
import com.education.common.ResultCode;
import com.education.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message(e.getMessage());
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public Result error(BadSqlGrammarException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(ResultCode.SQL_ERROR).message("SQL语法错误");
    }

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public Result error(EduException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(e.getCode()).message(e.getMsg());
    }

}
