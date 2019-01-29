package com.zhouwenqi.apihub.core.model.request;

import com.zhouwenqi.apihub.core.model.enums.RequestSource;

import java.io.Serializable;

/**
 * Reuqest - baisc
 * Created by zhouwenqi on 2019/1/28.
 */
public class ReqBase implements Serializable {
    // 请求来源
    private RequestSource requestSource;

    public RequestSource getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(RequestSource requestSource) {
        this.requestSource = requestSource;
    }
}
