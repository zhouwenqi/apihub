package com.zhouwenqi.apihub.core.utils;

import com.zhouwenqi.apihub.core.common.RegexParams;

import java.util.regex.Pattern;

/**
 * 正则校验
 * Created by zhouwenqi on 2019/1/29.
 */
public class RegexUtils {
    /**
     * 校验email格式
     * @param email 邮箱
     * @return
     */
    public static boolean isEmail(String email)
    {
        return null != email && Pattern.matches(RegexParams.REG_EMAIL,email);
    }

    /**
     * 校验手机号码格式
     * @param mobile 手机号码
     * @return
     */
    public static boolean isMobile(String mobile){
        return null != mobile && Pattern.matches(RegexParams.REG_MOBILE,mobile);
    }

    /**
     * 校验电话格式
     * @param phone 电话号码
     * @return
     */
    public static boolean isPhone(String phone){
        return null != phone && Pattern.matches(RegexParams.REG_PHONE,phone);
    }

    /**
     * 校验密码格式
     * @param password 密码
     * @return
     */
    public static boolean isPassword(String password){
        return null != password && Pattern.matches(RegexParams.REG_PWD,password);
    }

    /**
     * 校验是否可转成int数值
     * @param text 字符串
     * @return
     */
    public static boolean isInt(String text){
        return null != text && Pattern.matches(RegexParams.REG_INT,text);
    }
}
