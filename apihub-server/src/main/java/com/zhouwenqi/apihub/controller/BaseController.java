package com.zhouwenqi.apihub.controller;

import com.zhouwenqi.apihub.core.common.DateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Date;


/**
 * Controller - Basic
 * Created by zhouwenqi on 2019/1/24.
 */
public class BaseController {
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class,new DateEditor(true));
    }
    public RequestAttributes getContextAttribute(){
        return RequestContextHolder.getRequestAttributes();
    }
}
