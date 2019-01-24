package com.zhouwenqi.apihub.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.Decimal128;

import java.io.IOException;

/**
 * Decimal128 序列化成字符串
 * Created by zhouwenqi on 2019/1/23.
 */
public class Decimal128Serializer extends JsonSerializer<Decimal128> {
    @Override
    public void serialize(Decimal128 decimal128, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException,JsonProcessingException {
        jsonGenerator.writeString(decimal128.toString());
    }
}
