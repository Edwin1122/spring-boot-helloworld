package com.knf.dev.demo.springboothelloworld.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class NormalJwtUtil {

    /**
     * 签名秘钥
     */
    private static final String SECRET = "!@#$%YLC*&^%()95622SSxx";

    /**
     * 创建token
     *
     * @param json 需要放入token的参数，多个参数可以封装成json或者map
     * @return token
     */
    public static String createToken(JSONObject json) {

        try {
            //没有用RS256算法, 因此不用公钥解密
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            return JWT.create()
                    //主要内容放到了Subject里
                    .withSubject(json.toString())
                    .withIssuer("ylc")

                    //设置过期时间1min
                    .withExpiresAt(DateUtil.offsetMinute(new Date(), 1))
                    .withClaim("customString", "SelfDefineParam")
                    .withArrayClaim("customArray", new Integer[]{1, 2, 3})
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    /**
     * 校验token 合法性
     *
     * @param token to verify.
     */
    public static boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //算法一致
            JWTVerifier verifier = JWT.require(algorithm)
                    // 验证签发人是否相同
                    .withIssuer("ylc")
                    .build();
            /*
             * 校验：
             * 格式校验：header.payload.signature
             * 加密方式校验 Header中的alg
             * 签名信息校验，防篡改
             * 载体Payload 中公有声明字段校验
             */
            verifier.verify(token);

            return true;
        }   catch (JWTVerificationException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * 解析token
     *
     * @param token to decode.
     */
    public static void decodeToken(String token) {

        try {
            DecodedJWT jwt = JWT.decode(token);

            Map<String, Claim> claims = jwt.getClaims();
            Claim customStringClaim = claims.get("customString");
            Claim customArrayClaim = claims.get("customArray");

            String issuer = jwt.getIssuer();
            String subject = jwt.getSubject();

            System.out.println(customStringClaim.asString());
            System.out.println(Arrays.toString(customArrayClaim.asArray(Integer.class)));

            System.out.println(issuer);
            System.out.println(JSONUtil.parseObj(subject));
        } catch (JWTDecodeException exception) {
            //Invalid token
            System.out.println(exception.getMessage());
        }



    }


}
