package com.cvc.cvcms.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZZJ
 * @date 2021/3/14 19:14
 * @desc jwt工具类
 */
@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JwtTokenUtil {
    private String secret;
    private Long expiration;
    private String header;

    /**
     * 生成
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails, List<String> permission){
        Map<String,Object> claims = new HashMap<>(3);
        claims.put("sub",userDetails.getUsername());
        claims.put("created",System.currentTimeMillis());
        claims.put("permission",permission);
        return generateToken(claims);
    }

    /**
     * 获取用户名
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token){
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            username = null;
        }
        return username;
    }

    /**
     * 获取权限
     * @param token
     * @return
     */
    public List<String> getPermissionFromToken(String token){
        List<String> permission;
        try {
            Claims claims = getClaimsFromToken(token);
            permission = (List<String>) claims.get("permission");
        }catch (Exception e){
            permission = null;
        }
        return permission;
    }

    /**
     * 判断过期 true：过期
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token){
        try {
            Claims claims = getClaimsFromToken(token);
            Date date = claims.getExpiration();
            return date.before(new Date());
        }catch (Exception e){
            return true;
        }
    }

    /**
     * 刷新令牌
     * @param token
     * @return
     */
    public String refreshToken(String token){
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created",new Date());
            refreshedToken = generateToken(claims);
        }catch (Exception e){
            refreshedToken=null;
        }
        return refreshedToken;
    }

    /**
     * 判断是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token,UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 生成token
     * @param clamis
     * @return
     */
    private String generateToken(Map<String,Object> clamis){
        Date expirationDate = new Date(System.currentTimeMillis()+expiration);
        return Jwts.builder().setClaims(clamis)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            claims = null;
        }
        return claims;
    }
}
