package com.zhouwenqi.apihub.exception;

import com.zhouwenqi.apihub.model.response.ResponseModel;
import com.zhouwenqi.apihub.model.response.ResultCode;

/**
 * Exception - Parameter error
 * Created by zhouwenqi on 2019/1/24.
 */
public class ApihubParameterErrorException extends ApihubBaseException {
    public ApihubParameterErrorException(){
        this.setResponseModel(ResponseModel.getParameterError());
    }
    public ApihubParameterErrorException(String msg){
        super(ResultCode.RESULT_PARAMETER_ERROR,msg);
    }
    public ApihubParameterErrorException(ResponseModel responseModel){
        super(responseModel);
    }
}
