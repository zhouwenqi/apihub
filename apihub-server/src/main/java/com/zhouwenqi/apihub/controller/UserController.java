package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.entity.User;
import com.zhouwenqi.apihub.exception.ApihubFailedException;
import com.zhouwenqi.apihub.model.enums.RequestSource;
import com.zhouwenqi.apihub.model.response.ResponseModel;
import com.zhouwenqi.apihub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private UserService userService;

    @Transactional
    @GetMapping("info")
    @ResponseBody
    public ResponseModel info(Date pdate){
        Map<String,Object> data = new HashMap<String,Object>();
        data.put("auth","zhouwenqi");
        data.put("pdate",pdate);
        data.put("date",new Date());
        ResponseModel responseModel = ResponseModel.getSuccess();
        responseModel.addData(data);

        User user = new User();
        user.setCreateDate(new Date());
        user.setPwd("zhouwenqi.com");
        user.setRequestSource(RequestSource.ANDROID);
        user.setEmail("zhouwenqi1982@gmail.com");
        user.setEditDate(new Date());
        userService.insert(user,"users");

        responseModel.addData("user",user);
        if(null!=user.getId()) {
            throw new ApihubFailedException("服务器出错");
        }

        return responseModel;
    }
}
