package com.ibk.sb.restapi.app.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenUtil {

    // secretKey
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.validity}")
    private long TOKEN_VALIDITY;

    public String makeJwt(String userId, String utlinsttId) throws Exception {

        String jwtToken = null;

        try {
            //token Header 생성
            Map<String, Object> headerMap = new HashMap<String, Object>();
            headerMap.put("typ", "JWT");
            headerMap.put("alg", "HS256");

            //token Claims 생성
            Map<String, Object> claims = new HashMap<>();
            claims.put("USERID", userId);
            claims.put("UTLINSTTID", utlinsttId);

            @SuppressWarnings("deprecation")
            JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                    .setClaims(claims)
                    .setSubject(userId) // 유효검증용 subject 설정
                    .setIssuedAt(new Date(System.currentTimeMillis())) //생성일
                    //.setExpiration(new Date(System.currentTimeMillis() + 900000000)) //만료일
                    .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY)) //만료일
                    .signWith(SignatureAlgorithm.HS256, this.secretKey);

            jwtToken = builder.compact();

            /*
             * JWT 관련 테이블 검증 로직
             */
//            Map<String, Object> param = new HashMap<>();
//            param.put("userId", session.getAttribute("lgnMnbrId").toString());
//            param.put("utlinsttId", session.getAttribute("usisId").toString());
//            param.put("jwt", jwtToken);
//            //기존에 발급된 로그인 아이디로 된 JWT 모두 만료
//            jwtDao.expiredJwtByUserIdAndUtlinsttId(param);
//            //새로 발급 된 jwt 토큰 레코드 추가
//            jwtDao.insertJwt(param);
//            // 발급된 jwt를 session 에 셋팅
//            session.setAttribute("jwt", jwtToken);

        } catch (NullPointerException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return jwtToken;
    }

    /**
     * jwt Claim 확인
     * @param token
     * @return
     */
    public Map<String, String> getClaimFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        Map<String, String> claimsMap = new HashMap<>();
        for(Map.Entry<String, Object> entry : claims.entrySet()) {
            claimsMap.put(entry.getKey(), entry.getValue().toString());
        }
        return claimsMap;
    }

    /**
     * jwt Claim 확인
     * @param token
     * @return
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
