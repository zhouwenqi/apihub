package com.zhouwenqi.apihub.exception;

import com.zhouwenqi.apihub.model.response.ResponseModel;
import com.zhouwenqi.apihub.model.response.ResultCode;

/**
 * Exception - Not token
 * Created by zhouwenqi on 2019/1/24.
 */
public class ApihubNotTokenException extends ApihubBaseException {
    public ApihubNotTokenException(){
        this.setResponseModel(ResponseModel.getNotToken());
    }
    public ApihubNotTokenException(String msg){
        super(ResultCode.RESULT_NOT_TOKEN,msg);
    }
    public ApihubNotTokenException(ResponseModel responseModel){
        super(responseModel);
    }
}
