package com.ibk.sb.restapi.biz.api.login;

import com.ibk.sb.restapi.app.common.constant.StatusCode;
import com.ibk.sb.restapi.app.common.jwt.JwtTokenUtil;
import com.ibk.sb.restapi.app.common.util.BizException;
import com.ibk.sb.restapi.app.common.vo.CustomUser;
import com.ibk.sb.restapi.app.common.vo.ResponseData;
import com.ibk.sb.restapi.biz.service.login.LoginService;
import com.ibk.sb.restapi.biz.service.mainbox.MainBoxService;
import com.ibk.sb.restapi.biz.service.seller.vo.SellerInfoVO;
import com.ibk.sb.restapi.biz.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(tags = {"커머스 박스 - 로그인 API"})
@RestController
@Slf4j
@RequestMapping(path = {"/api/login", "/api/mk/v1/login"}, produces={MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    // 메인박스 로그인 연동(Magic SSO 연동시)
    //private final MainBoxService mainBoxService;

    private final JwtTokenUtil jwtTokenUtil;

    private final Environment environment;

    private final UserService userService;

    /**
     *  로그인 JWT 발급
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "로그인 JWT 발급")
    @ApiImplicitParam(name = "loginMap", value = "type, auth")
    @RequestMapping()
    public ResponseData login(@RequestBody Map<String, String> loginMap) throws Exception {

        try {

            // String type = request.getParameter("type");
            // request.setAttribute("type", type);
            // String auth = LoginUtil.getCookie(request, "auth");

            String type = loginMap.get("type");
            String auth = loginMap.get("auth");

            if (!StringUtils.hasLength(type) || !StringUtils.hasLength(auth)) {
                throw new Exception("필수입력값을 확인해주세요");
            }

            // 메인박스 로그인 세션 정보 조회
            if ("2".equals(type)) {
                //JSONObject reqJson = new JSONObject();
                HashMap<String, String> reqJson = new HashMap<>();
                reqJson.put("ssnUuid", auth);

                // 세션관리 테이블에서 정보 취득
                //ResultListDto<JSONObject> res = new ResultListDto<JSONObject>();
                //res = comService.login(reqJson.get("authorization"), reqJson);
                Map<String, Object> res = loginService.login(reqJson);

                String lgnMnbrId = (String) res.get("lgnMnbrId");
                String usisId = (String) res.get("usisId");

                List<String> loginProfiles = new ArrayList<>();
                if(environment.getActiveProfiles() != null) {
                    loginProfiles = Arrays.asList(environment.getActiveProfiles());
                }

                if(loginProfiles.size() > 0 && !loginProfiles.contains("prod")){
                    switch (lgnMnbrId) {
                        // seulki
                        case "a6foT5p08A" :
                            lgnMnbrId = "box13701";
                            usisId = "C0000142";
                            break;

                        // siyun
                        case "3V54N6rZT6" :
                            lgnMnbrId = "box07401";
                            usisId = "C0000079";
                            break;

                        // eunji
                        case "OnXmh8wT7H" :
                            lgnMnbrId = "box14601";
                            usisId = "C0000151";
                            break;
                    }
                }

                String jwt = jwtTokenUtil.makeJwt(lgnMnbrId, usisId);

                // 회원 타입, 회원 상태 조회
                SellerInfoVO sellerInfoVO = userService.searchSellerInfo(lgnMnbrId, usisId);

                // 회원타입이 존재하지 않는 경우, 개인회원일 경우
                if(sellerInfoVO == null) {
                    sellerInfoVO = new SellerInfoVO();
                }

                return ResponseData.builder()
                        .code(HttpStatus.OK.value())
                        .token(jwt)
                        .data(sellerInfoVO.getMmbrtypeId())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .build();
            }

            // 로그인 타입이 올바르지 않을 경우
            else {
                return ResponseData.builder().code(HttpStatus.BAD_REQUEST.value()).message(HttpStatus.BAD_REQUEST.getReasonPhrase()).build();
            }

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0000.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0000.getMessage())
                    .build();
        }
    }

    /**
     * JWT 체크 및 재발급
     * @return
     */
    @ApiOperation(value = "JWT 체크 및 재발급")
    @GetMapping("/jwt/check")
    public ResponseData jwtCheck(HttpServletRequest request) {

        try {
            String jwt = null;

            SellerInfoVO sellerInfoVO = null;

            // 로그인 체크
            // 로그인 정보가 CustomUser.class 라면 jwt 로그인 조회
            // 로그인 상태가 아니라면 String 타입으로 떨이짐
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof CustomUser) {
                CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                jwt = jwtTokenUtil.makeJwt(user.getLoginUserId(), user.getUtlinsttId());

                // 회원 타입, 회원 상태 조회
                sellerInfoVO = userService.searchSellerInfo(user.getLoginUserId(), user.getUtlinsttId());

                // 회원타입이 존재하지 않는 경우, 개인회원일 경우
                if(sellerInfoVO == null) {
                    sellerInfoVO = new SellerInfoVO();
                }
            }

            return ResponseData.builder()
                    .code(HttpStatus.OK.value())
                    .token(jwt)
                    .data(sellerInfoVO.getMmbrtypeId())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .build();

        } catch (BizException ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0000.getMessage())
                    .build();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseData.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(StatusCode.COM0000.getMessage())
                    .build();
        }
    }

//    /**
//     * 로그아웃
//     * @return
//     */
//    @PostMapping("/logout")
//    public ResponseData logout(HttpServletRequest request) {
//
//        try {
//            String requestTokenHeader = request.getHeader("Authorization");
//            String jwtToken = "";
//
//            // 토큰 존재 체크
//            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//                jwtToken = requestTokenHeader.substring(7);
//            }
//
//            // 토큰이 없거나 올바른 형식으로 토큰을 받을 수 없는 경우, 에러로 리턴
//            else {
//                return ResponseData.builder().code(HttpStatus.BAD_REQUEST.value()).message(StatusCode.COM0000.getMessage()).build();
//            }
//
//            Map<String, Object> resultMap = mainBoxService.logout(jwtToken);
//
//            return ResponseData.builder()
//                    .code(HttpStatus.OK.value())
//                    .message(HttpStatus.OK.getReasonPhrase())
//                    .data(resultMap)
//                    .build();
//
//        } catch (BizException ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(StatusCode.COM0000.getMessage())
//                    .build();
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return ResponseData.builder()
//                    .code(HttpStatus.BAD_REQUEST.value())
//                    .message(StatusCode.COM0000.getMessage())
//                    .build();
//        }
//    }
}
