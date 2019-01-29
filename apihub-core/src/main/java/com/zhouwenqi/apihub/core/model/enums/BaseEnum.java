package com.zhouwenqi.apihub.core.model.enums;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * enum - basic
 * Created by zhouwenqi on 2019/1/25.
 */
public interface BaseEnum {
    String INDEX_NAME = "index";
    String TAG_NAME = "tag";
    default Integer getIndex(){

        Field field = ReflectionUtils.findField(this.getClass(),INDEX_NAME);
        if(field == null){
            return null;
        }
        try{
            field.setAccessible(true);
            return Integer.parseInt(field.get(this).toString());
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    default String getTag(){
        Field field = ReflectionUtils.findField(this.getClass(),TAG_NAME);
        if(field == null){
            return null;
        }
        try{
            field.setAccessible(true);
            return field.get(this).toString();
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    static <T extends Enum<T>> T indexOfEnum(Class<T> enumClass, Integer index) {
        if(index == null){
            throw new IllegalArgumentException("BaseEnum value should not be null");
        }
        if(enumClass.isAssignableFrom(BaseEnum.class)){
            throw new IllegalArgumentException("illegal BaseEnum type");
        }
        T[] enums = enumClass.getEnumConstants();
        for(T t:enums){
            BaseEnum displayedEnum = (BaseEnum)t;
            if(displayedEnum.getIndex().equals(index)){
                return (T)displayedEnum;
            }
        }
        throw new IllegalArgumentException("cannot parse integer: " + index + " to " + enumClass.getName());
    }
}
