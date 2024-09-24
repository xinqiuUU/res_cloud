package com.yc.utils;

import com.yc.ResSecurityApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResSecurityApp.class)
@Slf4j
class JwtTokenUtilTest {

    @Test
    void encodeJWT() {
        String r = JwtTokenUtil.encodeJWT("aaaaaadasdasdasdasdasdasasdasdasdasdasdasdasdadasdada");
        log.info(r);
    }

    @Test
    void decodeJWT() {
        JwtTokenUtil.decodeJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLmtYvor5Vqd3TnlJ_miJB0b2tlbiIsInJvbGUiOiJhZG1pbiIsIm5hbWUiOiLlvKDkuIkiLCJleHAiOjE3MjUzNTU4NDEzMTgsImlhdCI6IjI5MzQxZDY1LWRlMjEtNGVjYi1hODE4LWM5NmVkYzEzYjNlOCJ9.JsTId1SB0g6nipkGog8exuZDgf55Gc_yc6IRglSUc7A"
        , "aaaaaadasdasdasdasdasdasasdasdasdasdasdasdasdadasdada");
    }
}