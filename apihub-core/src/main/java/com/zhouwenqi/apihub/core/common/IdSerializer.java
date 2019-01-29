package com.zhouwenqi.apihub.core.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * ObjectId 序列化字符串
 * Created by zhouwenqi on 2019/1/24.
 */
public class IdSerializer extends JsonSerializer<ObjectId> {
    @Override
    public void serialize(ObjectId id, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException{
        jsonGenerator.writeString(id.toString());
    }
}
