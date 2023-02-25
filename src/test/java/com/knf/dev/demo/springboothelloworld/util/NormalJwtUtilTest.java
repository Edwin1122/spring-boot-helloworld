package com.knf.dev.demo.springboothelloworld.util;


import cn.hutool.json.JSONObject;
import org.junit.jupiter.api.Test;


class NormalJwtUtilTest {

    @Test
    void verifyToken(){

        JSONObject subjectJson = new JSONObject();
        subjectJson.put("userId", 8888);
        subjectJson.put("name", "ylc");

        String token = NormalJwtUtil.createToken(subjectJson);
        System.out.println("token:" + token);
        System.out.println("===================");

        System.out.println("Verify result: " + NormalJwtUtil.verifyToken(token));
        System.out.println("===================");


        System.out.println("Decode info: ");
        NormalJwtUtil.decodeToken(token);

    }
}