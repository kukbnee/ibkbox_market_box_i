package com.ibk.sb.restapi.app.common.jwt;

import com.ibk.sb.restapi.app.common.constant.ComCode;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.mainbox.vo.MainUserVO;
import com.ibk.sb.restapi.biz.service.user.UserService;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter  {

    private final JwtTokenUtil jwtTokenUtil;

    private final MainBoxService mainBoxService;

    private final UserService userService;

    //    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, RuntimeException {

        final String requestTokenHeader = request.getHeader("Authorization");

        // 로그인한 유저 ID
        String username = null;
        // 로그인한 유저 운영기관 ID
        String companyname = null;
        String jwtToken = null;

        try {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                jwtToken = requestTokenHeader.substring(7);

                Map<String, String> jwtClaimsMap = jwtTokenUtil.getClaimFromToken(jwtToken);
                username = jwtClaimsMap.get("USERID");
                companyname = jwtClaimsMap.get("UTLINSTTID");
            }

            // 토큰에 들어 있는 아이디가 있고 스프링 컨텍스트에 인증이 되어 있지 않을 경우
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 메인박스 로그인 체크(Magic SSO 연동시)
                // mainBoxService.checkJwtValidate(username, companyname, jwtToken);

                //들어온 유저를 검색해서 체크
                CustomUser userDetails = this.loadUserDetail(username, companyname);

                if (username.equals(userDetails.getUsername()) && companyname.equals(userDetails.getUtlinsttId()) && !isTokenExpired(jwtToken)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token has expired");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        filterChain.doFilter(request,response);
    }

    /**
     * 토큰 유효기간 체크
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = jwtTokenUtil.getClaimFromToken(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    /**
     * tempLogin jwt filter 에서 토큰 유효성 검사용 UserDetail 작성
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @ResponseBody
    public CustomUser loadUserDetail(String userId, String companyname) throws RuntimeException {

        try {

            // 메인BOX 사용자 정보 조회
            MainUserVO mainUserVO = mainBoxService.searchMainUser(userId, companyname);

            // 회원 타입, 회원 상태 조회
            SellerInfoVO sellerInfoVO = userService.searchSellerInfo(userId, companyname);

            // 회원타입이 존재하지 않는 경우, 개인회원일 경우
            if(sellerInfoVO == null) {
                sellerInfoVO = new SellerInfoVO();
            }

            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            // 권한 설정
            String authorCode = mainUserVO.getAuthorCode() != null || StringUtils.hasLength(mainUserVO.getAuthorCode()) ? mainUserVO.getAuthorCode() : "USER";
            authorities.add(new SimpleGrantedAuthority(authorCode));

            CustomUser userDetails = new CustomUser(userId,companyname,null, authorities, sellerInfoVO.getMmbrtypeId(), mainUserVO.getBizrno());

            return userDetails;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("catch exception =====> loadUserDetail");
        }

    }
}