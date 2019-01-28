package com.zhouwenqi.apihub.common;

import org.apache.commons.lang.time.DateUtils;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date 入参序列化
 * Created by zhouwenqi on 2019/1/25.
 */
public class DateEditor extends PropertyEditorSupport {
    // 默认日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // 可转换的日期格式
    public static final String[] DATE_PATTERNS = new String[]{"yyyy-MM", "yyyy/MM", "yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"};
    // 是否将空数据转换为null
    private boolean isEmptyAsNull;
    // 初始化日期格式
    private String dateFormat = DATE_FORMAT;

    /**
     * 日期类型转换构造函数
     * @param isEmptyAsNull 是否将空数据转换为null
     */
    public DateEditor(boolean isEmptyAsNull){
        this.isEmptyAsNull = isEmptyAsNull;
    }

    /**
     * 日期类型转换构造函数
     * @param isEmptyAsNull 是否将空数据转换为null
     * @param dateFormat 日期格式
     */
    public DateEditor(boolean isEmptyAsNull, String dateFormat){
        this.isEmptyAsNull = isEmptyAsNull;
        this.dateFormat = dateFormat;
    }

    /**
     * 获取转换后的日期
     * @return 日期
     */
    @Override
    public String getAsText(){
        Date value=(Date)getValue();
        return null != value ? new SimpleDateFormat(dateFormat).format(value) : "";
    }

    /**
     * 设置需要转换的日期字符串
     * @param text 日期字符串
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException{
        if(null==text){
            setValue(null);
        }else{
            String value = text.trim();
            if(isEmptyAsNull && "".equals(value)){
                setValue(null);
            }else{
                try{
                    setValue(DateUtils.parseDate(value,DATE_PATTERNS));
                }catch (Exception e){
                    setValue(null);
                }
            }
        }
    }
}
