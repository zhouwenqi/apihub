package com.zhouwenqi.apihub.core.model.response;

import com.zhouwenqi.apihub.core.utils.JsonUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * 过滤器和拦截器指定输出模型
 * Created by zhouwenqi on 2019/1/28.
 */
public class ResponseResult {
    public static void output(ResponseModel responseModel,HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(responseModel.getCode());
        String data = JsonUtils.objectToJson(responseModel);
        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(data.getBytes("utf-8"));
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
