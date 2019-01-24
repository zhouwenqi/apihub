package com.zhouwenqi.apihub.exception;

import com.zhouwenqi.apihub.model.response.ResponseModel;

/**
 * Exception - Apihub
 * Created by zhouwenqi on 2019/1/24.
 */
public class ApihubBaseException extends RuntimeException {
    public ResponseModel responseModel;
    public ApihubBaseException(){

    }
    public ApihubBaseException(int errorCode,String message) {
        this.responseModel = new ResponseModel(errorCode,message);
    }
    public ApihubBaseException(ResponseModel responseModel){
        this.responseModel = responseModel;
    }

    public ResponseModel getResponseModel() {
        return responseModel;
    }

    public void setResponseModel(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }
}
