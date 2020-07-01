package com.education.common;

public interface ResultCode {

    String SUCCESS = "SUCCESS";
    String FAILURE = "FAILURE";

    // 成功
    int OK = 20000;
    // 失败
    int ERROR = 20001;
    // sql 错误
    int SQL_ERROR = 20006;
    // ID 错误
    int EDU_ID_ERROR = 20007;
}
