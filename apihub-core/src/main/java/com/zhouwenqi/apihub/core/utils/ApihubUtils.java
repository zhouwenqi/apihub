package com.zhouwenqi.apihub.core.utils;

import java.util.Random;

/**
 * Created by zhouwenqi on 2019/1/29.
 */
public class ApihubUtils {
    /**
     * 获取随机验证码
     * @param length 验证码长度
     * @return
     */
    public static String getVerifyCode(int length){
        StringBuffer stringBuffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyz");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i=0;i<length;i++){
            sb.append(stringBuffer.charAt(random.nextInt(stringBuffer.length())));
        }
        return sb.toString();
    }
}
