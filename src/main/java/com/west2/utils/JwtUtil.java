package com.west2.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author 1
 * jwt工具类
 */

@Slf4j
@Component
public class JwtUtil {
    /**
     * token请求头名称
     */
    public  final String TOKEN_HEADER = "Authorization";

    /**
     * token的redis缓存前缀
     */
    public  final String TOKEN_CACHE_PREFIX = "token:";

    /**
     * 有效期为60 * 60
     */
    public  final int JWT_TTL = 1000 * 60 * 60 * 2 ;

    /**
     * 设置秘钥明文
     */
    @Value("${my.jwt.secretKey}")
    private String JWT_SECRET;


    /**
     * 创建jwt
     * @param userInfo
     * @return
     */
    public  String createJwt(String userInfo, List<String> authList) {
        log.info("JWT_SECRET:{}", JWT_SECRET);
        //签发时间时间
        Date issDate = new Date();
        //当前时间加上两个小时
        Date expireDate = new Date(issDate.getTime() + JWT_TTL);
        //头部
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");
        return JWT.create().withHeader(headerClaims)
                //设置签发人
                .withIssuer("wp")
                //签发时间
                .withIssuedAt(issDate)
                .withExpiresAt(expireDate)
                //自定义声明 放入登录用户信息
                .withClaim("userInfo", userInfo)
                //自定义声明 放入用户权限信息
                .withClaim("userAuth", authList)
                //使用HS256进行签名，JWT_SECRET作为密钥
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    public  boolean verifyToken(String jwtToken){
        //创建校验器
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
            //校验token
            DecodedJWT decodedJwt = jwtVerifier.verify(jwtToken);
            log.info("token验证正确");
            return true;
        } catch (Exception e) {
            log.error("token验证不正确！！");
            return false;
        }
    }

    /**
     * 从jwt的payload里获取声明，获取的用户的信息
     * @param jwt
     * @return
     */
    public  String getUserInfoFromToken(String jwt){
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            return decodedJWT.getClaim("userInfo").asString();
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return "";
        }
    }

    /**
     * 从jwt的payload里获取声明，获取的用户的权限
     * @param jwt
     * @return
     */
    public List<String> getUserAuthFromToken(String jwt){
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
            return decodedJWT.getClaim("userAuth").asList(String.class);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            return null;
        }
    }
}
