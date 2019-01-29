package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.model.response.ResponseModel;
import com.zhouwenqi.apihub.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by zhouwenqi on 2019/1/24.
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping("info")
    @ResponseBody
    public ResponseModel info(Date pdate){
        ResponseModel responseModel = ResponseModel.getSuccess();
        User user = new User();
        user.setUid("zhouwenqi");
        String pwd = DigestUtils.md5Hex("123456").toLowerCase();
        user.setPwd(pwd);
        user.setEmail("zhouwenqi1982@live.com");
        user.setCreateDate(new Date());
        user.setEditDate(new Date());
        userService.insert(user,"user");
        return responseModel;
    }
}
