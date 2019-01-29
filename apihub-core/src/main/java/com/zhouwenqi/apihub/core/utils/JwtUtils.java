package com.zhouwenqi.apihub.core.utils;

import com.zhouwenqi.apihub.core.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt
 * Created by zhouwenqi on 2019/1/29.
 */
public class JwtUtils {
    final static String TOKEN_KEY = "apihub-token";
    final static String API_SUBJECT="apihub-jwt";

    /**
     * 根据用户信息生成token
     * @param user 用户信息
     * @return token字符串
     */
    public static String encode(JwtUser user){
        Map<String,String> info = new HashMap<String, String>();
        info.put("userId",user.getId().toString());
        info.put("uid",user.getUid());
        String subject = API_SUBJECT;
        Date expireDate = getExpireDate(7 * 24 * 60);
        return encode(info,subject,expireDate);
    }

    /**
     * 自定义生成Jwt信息
     * @param info 需要生成的字段
     * @param subject 标题
     * @param expireDate 过期时间
     * @return
     */
    public static String encode(Map<String,String> info,String subject,Date expireDate){
        // 设置需要生成的参数
        JwtBuilder builder = Jwts.builder().setSubject(subject);
        for(Map.Entry<String,String> map:info.entrySet()){
            String key = map.getKey();
            String value = map.getValue();
            builder.claim(key,value);
        }
        // 设置过期时间
        builder.setExpiration(expireDate);
        return builder.signWith(SignatureAlgorithm.HS512,TOKEN_KEY).compact();
    }

    /**
     * 解密token
     * @param token
     * @return claims对象
     * @throws Exception
     */
    public static Claims decode(String token) throws Exception{
        return Jwts.parser().setSigningKey(TOKEN_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * 解密token
     * @param token
     * @return user信息
     * @throws Exception
     */
    public static JwtUser decodeUser(String token) throws Exception{
        Claims claims = decode(token);
        JwtUser user = new JwtUser();
        user.setUid(claims.get("uid").toString());
        user.setId(claims.get("userId").toString());
        return user;
    }

    /**
     * 获取到期时间
     * @param minutes 到期指定的分钟总数，默认7天
     * @return 到期时间
     */
    public static Date getExpireDate(Integer minutes){
        int minute = 7 * 24 * 60;
        if(null!=minutes){
            minute = minutes;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,minute);
        return calendar.getTime();
    }
}
