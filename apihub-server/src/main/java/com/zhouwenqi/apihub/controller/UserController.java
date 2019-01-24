package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.exception.ApihubBaseException;
import com.zhouwenqi.apihub.model.response.ResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouwenqi on 2019/1/24.
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @GetMapping("info")
    @ResponseBody
    public ResponseModel info(Date pdate){
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("auth","zhouwenqi");
        data.put("pdate",pdate);
        data.put("date",new Date());
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData(data);

        return responseModel;
    }
}
