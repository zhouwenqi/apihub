package com.zhouwenqi.apihub.model.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * response - apihub
 * Created by zhouwenqi on 2019/1/24.
 */
public class ResponseModel implements Serializable{
    // 返回状态码
    private int code;
    // 返回消息
    private String msg;
    // 返回数据
    private Map<String,Object> data;

    /**
     * 构造函数
     * @param code 状态码
     * @param msg 消息
     */
    public ResponseModel(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * 构造函数
     * @param code 状态码
     */
    public ResponseModel(int code){
        this.code = code;
    }

    /**
     * 构造一个返回成功的模型
     * @return
     */
    public static ResponseModel getSuccess(){
        return new ResponseModel(ResultCode.RESULT_SUCCESS,"请求成功");
    }


    /**
     * 构造一个返回失败的模型
     * @return
     */
    public static ResponseModel getFailed(){
        return new ResponseModel(ResultCode.RESULT_FAILED,"请求失败");
    }

    /**
     * 构建返回缺少参数的模型
     * @return
     */
    public static ResponseModel getNotParameter(){
        return new ResponseModel(ResultCode.RESULT_NOT_PARAMETER,"缺少参数");
    }

    /**
     * 构建返回参数错误的模型
     * @return
     */
    public static ResponseModel getParameterError(){
        return new ResponseModel(ResultCode.RESULT_PARAMETER_ERROR,"参数错误");
    }

    /**
     * 构建返回token无效的模型
     * @return
     */
    public static ResponseModel getNotToken(){
        return new ResponseModel(ResultCode.RESULT_NOT_TOKEN,"需要token凭据");
    }

    /**
     * 构建返回token无效的模型
     * @return
     */
    public static ResponseModel getTokenError(){
        return new ResponseModel(ResultCode.RESULT_TOKEN_ERROR,"无效的token");
    }

    /**
     * 构建返回权限不够的模型
     * @return
     */
    public static ResponseModel getNotAuthority(){
        return new ResponseModel(ResultCode.RESULT_NOT_AUTHORITY,"权限不够");
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    /**
     * 追加返回数据
     * @param data 追加的数据
     */
    public void addData(Map<String,Object> data){
        if(null==data){
            return;
        }
        if(null==this.data){
            this.data = new HashMap<String,Object>();
        }
        this.data.putAll(data);
    }

    /**
     * 追加返回数据
     * @param key 追加的数据的key
     * @param object 追加的数据
     */
    public void addData(String key,Object object){
        if(null==object){
            return;
        }
        if(null==this.data){
            this.data = new HashMap<String,Object>();
        }
        this.data.put(key,object);
    }


    public void setData(Map<String, Object> data) {
        this.data = data;
    }


}
