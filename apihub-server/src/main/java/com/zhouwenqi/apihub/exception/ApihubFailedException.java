package com.zhouwenqi.apihub.exception;

import com.zhouwenqi.apihub.model.response.ResponseModel;
import com.zhouwenqi.apihub.model.response.ResultCode;

/**
 * Exception - Failed
 * Created by zhouwenqi on 2019/1/24.
 */
public class ApihubFailedException extends ApihubBaseException {
    public ApihubFailedException(){
        this.setResponseModel(ResponseModel.getFailed());
    }
    public ApihubFailedException(String msg){
        super(ResultCode.RESULT_FAILED,msg);
    }
    public ApihubFailedException(ResponseModel responseModel){
        super(responseModel);
    }
}
