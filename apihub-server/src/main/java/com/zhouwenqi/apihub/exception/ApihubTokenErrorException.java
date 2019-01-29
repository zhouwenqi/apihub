package com.zhouwenqi.apihub.exception;


import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.core.model.response.ResultCode;

/**
 * Exception - Token error
 * Created by zhouwenqi on 2019/1/24.
 */
public class ApihubTokenErrorException extends ApihubBaseException {
    public ApihubTokenErrorException(){
        this.setResponseModel(ResponseModel.getTokenError());
    }
    public ApihubTokenErrorException(String msg){
        super(ResultCode.RESULT_TOKEN_ERROR,msg);
    }
    public ApihubTokenErrorException(ResponseModel responseModel){
        super(responseModel);
    }
}
