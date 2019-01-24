package com.zhouwenqi.apihub.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bson.types.Decimal128;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 字符串序列化 Decimal128
 * Created by zhouwenqi on 2019/1/23.
 */
public class Decimal128Deserializer extends JsonDeserializer<Decimal128> {
    @Override
    public Decimal128 deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
        return new Decimal128(new BigDecimal(jsonParser.getText()));
    }
}
