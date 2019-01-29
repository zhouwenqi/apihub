package com.zhouwenqi.apihub.exception;


import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.core.model.response.ResultCode;

/**
 * Exception - Not parameter
 * Created by zhouwenqi on 2019/1/24.
 */
public class ApihubNotParameterException extends ApihubBaseException {
    public ApihubNotParameterException(){
        this.setResponseModel(ResponseModel.getNotParameter());
    }
    public ApihubNotParameterException(String msg){
        super(ResultCode.RESULT_FAILED,msg);
    }
    public ApihubNotParameterException(ResponseModel responseModel){
        super(responseModel);
    }
}
