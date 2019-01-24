package com.zhouwenqi.apihub.exception;

import com.zhouwenqi.apihub.model.response.ResponseModel;
import com.zhouwenqi.apihub.model.response.ResultCode;

/**
 * Exception - Not authority
 * Created by zhouwenqi on 2019/1/24.
 */
public class ApihubNotAuthorityException extends ApihubBaseException {
    public ApihubNotAuthorityException(){
        this.setResponseModel(ResponseModel.getNotAuthority());
    }
    public ApihubNotAuthorityException(String msg){
        super(ResultCode.RESULT_NOT_AUTHORITY,msg);
    }
    public ApihubNotAuthorityException(ResponseModel responseModel){
        super(responseModel);
    }
}
