package com.zhouwenqi.apihub.exception;

import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * Handler - Apihub
 * Created by zhouwenqi on 2019/1/24.
 */
@RestControllerAdvice
@ResponseBody
public class ApihubExceptionHandler {
    @ExceptionHandler(ApihubBaseException.class)
    public ResponseModel apihubException(ApihubBaseException e, HttpServletResponse response) throws Exception {
        ResponseModel responseModel = e.getResponseModel();
        response.setStatus(responseModel.getCode());
        return responseModel;
    }
}
