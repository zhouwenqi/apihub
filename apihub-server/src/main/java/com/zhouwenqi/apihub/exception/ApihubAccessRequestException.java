package com.zhouwenqi.apihub.exception;

import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.core.model.response.ResultCode;

/**
 * Exception - Access request
 * Created by zhouwenqi on 2019/1/28.
 */
public class ApihubAccessRequestException extends ApihubBaseException {
    public ApihubAccessRequestException(){
        this.setResponseModel(ResponseModel.getAccessRequest());
    }
    public ApihubAccessRequestException(String msg){
        super(ResultCode.RESULT_ACCESS_REQUEST,msg);
    }
    public ApihubAccessRequestException(ResponseModel responseModel){
        super(responseModel);
    }
}
