package com.zhouwenqi.apihub.controller;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * Controller - Basic
 * Created by zhouwenqi on 2019/1/24.
 */
public class BaseController {
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
    public RequestAttributes getContextAttribute(){
        return RequestContextHolder.getRequestAttributes();
    }
}
