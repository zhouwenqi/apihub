package com.zhouwenqi.apihub.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * 字符串序列化为 ObjectId
 * Created by zhouwenqi on 2019/1/24.
 */
public class IdDeserializer extends JsonDeserializer<ObjectId> {
    @Override
    public ObjectId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException,JsonProcessingException {
        return new ObjectId(jsonParser.getText());
    }
}
