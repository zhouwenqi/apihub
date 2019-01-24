package com.zhouwenqi.apihub.model.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * response - model
 * Created by zhouwenqi on 2019/1/24.
 */
public class ApihubModel implements Serializable {
    // 状态码
    private int code;
    // 消息
    private String msg;
    // 消息数据
    private Map<String,Object> data;

    public ApihubModel(int code,String msg){
        this.code = code;
        this.msg = msg;
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

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void addData(Map<String,Object> data){
        if(null == data){
            return;
        }
        if(null == this.data){
            this.data = new HashMap<String,Object>();
        }
        this.data.putAll(data);
    }

    public void addData(String key,Object object){
        if(null == object){
            return;
        }
        if(null == this.data){
            this.data = new HashMap<String,Object>();
        }
        this.data.put(key,object);
    }
}
