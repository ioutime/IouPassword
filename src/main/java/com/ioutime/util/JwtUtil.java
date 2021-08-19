package com.ioutime.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ioutime
 * @version 1.0
 * @date 2021/8/16 14:32
 */

public class JwtUtil {
    //私钥
    private static final String SIGN = "!@#$%^&";
    /**
     *生成token
     */
    public String jwtgetToken(HashMap<String,String> hashMap) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR,1);//默认过期时间1天
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //header 默认可以不写
        //payload
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            builder.withClaim(entry.getKey(),entry.getValue());
        }
        //生成token
        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGN));

        return token;
    }

    /**
     *验证token
     */
    public static boolean verifys(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SIGN)).build();
            DecodedJWT verify = jwtVerifier.verify(token);
            return true;
        } catch (Throwable e){
            return false;
        }
    }

    /*解析*/
    public static int getInfo(String token){
        try{
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SIGN)).build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            Claim claim = jwt.getClaim("uid");
            String s = claim.asString();
            int uid = Integer.parseInt(s);
            return uid;
        }catch (Throwable e){
            return -1;
        }
    }

}
