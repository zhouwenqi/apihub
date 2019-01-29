package com.zhouwenqi.apihub.core.model.response;

/**
 * 返回状态码
 * Created by zhouwenqi on 2019/1/24.
 */
public class ResultCode {
    // 请求成功
    public static int RESULT_SUCCESS = 200;
    // 请求失败
    public static int RESULT_FAILED = 700;
    // 缺少参数
    public static int RESULT_NOT_PARAMETER = 701;
    // 参数错误
    public static int RESULT_PARAMETER_ERROR = 702;
    // 缺少凭据
    public static int RESULT_NOT_TOKEN = 900;
    // 凭据无效
    public static int RESULT_TOKEN_ERROR = 901;
    // 权限不够
    public static int RESULT_NOT_AUTHORITY = 902;
    // 拒绝访问
    public static int RESULT_ACCESS_REQUEST = 903;
}
