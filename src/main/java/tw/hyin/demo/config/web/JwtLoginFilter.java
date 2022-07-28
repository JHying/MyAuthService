package tw.hyin.demo.config.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import tw.hyin.demo.entity._UserToken;
import tw.hyin.demo.repo._UserTokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import tw.hyin.demo.config.KeyConfig;
import tw.hyin.demo.dto.TokenReq;
import tw.hyin.java.utils.GzipStrUtil;
import tw.hyin.java.utils.JsonUtil;
import tw.hyin.java.utils.Log;
import tw.hyin.java.utils.http.ResponseObj;
import tw.hyin.java.utils.security.JwtUtil;

/**
 * jwt 登入 (有效期12小時)
 *
 * @author H-yin
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    //    private final RedisTemplate<String, Object> redisTemplate;
    private final _UserTokenRepository tokenRepository;

    public JwtLoginFilter(String url, AuthenticationManager authManager, _UserTokenRepository tokenRepository) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        //        this.redisTemplate = redisTemplate;
        this.tokenRepository = tokenRepository;
    }

    /**
     * 登入驗證
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        // JSON 反序列化存放於 TokenReq
        TokenReq tokenReq = new ObjectMapper().readValue(req.getInputStream(), TokenReq.class);
        //先確認登入狀態
        //        if (!this.isUserLogin(tokenReq.getUserId())) {
        //            return getAuthenticationManager()
        //                    .authenticate(new UsernamePasswordAuthenticationToken("user is not allowed", null));
        //        } else {
        // 導向 AuthenticationManager 重新定義之 Provider 驗證，並返回驗證狀態
        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(
                        tokenReq.getUserId() == null ? tokenReq.getJwtUser() : tokenReq.getUserId(), tokenReq));
        //        }
    }

    /**
     * 身分驗證成功
     * payload = userName
     */
    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) {
        // JSON 反序列化存放於 TokenReq
        String userId = auth.getPrincipal().toString();
        Log.info(">> [" + userId + "]: JWT Login.");
        //確認是否有未過期 token
        Date now = new Date();
        _UserToken userToken = tokenRepository.findTokenNotExpired(userId, now);
        String token;
        if (userToken == null) {
            // 使用 RSA 密鑰生成新的 token 通行證, 有效期 60 分鐘 * 24
            int expiredMillis = 60 * 60 * 1000 * 24;
            token = JwtUtil.generateToken(userId, KeyConfig.privateKey, expiredMillis);
            //儲存新token (先壓縮, 壓縮後長度約 576)
            Date expiredTime = new Date(now.getTime() + expiredMillis);
            tokenRepository.save(new _UserToken(GzipStrUtil.compress(token), userId, expiredTime, now));
        } else {
            //繼續使用舊的token (先將token還原)
            token = GzipStrUtil.decompress(userToken.getToken());
        }
        ResponseObj<String> responseObj = new ResponseObj<>(HttpStatus.OK, null, token);
        res.setContentType("application/json");
        res.setStatus(HttpServletResponse.SC_OK);
        res.getOutputStream().println(JsonUtil.objToJson(responseObj));
    }

    /**
     * 身分驗證失敗
     */
    @SneakyThrows
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        List<String> errors = new ArrayList<>();
        errors.add("message: " + failed.getMessage());
        Log.error("message: " + failed.getMessage());
        ResponseObj<Object> responseObj = new ResponseObj<>(HttpStatus.UNAUTHORIZED, errors, null);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(JsonUtil.objToJson(responseObj));
    }

    //    private boolean isUserLogin(String userId) throws AuthException {
    //        return this.redisTemplate.opsForValue().get(userId) != null;
    //    }

}
