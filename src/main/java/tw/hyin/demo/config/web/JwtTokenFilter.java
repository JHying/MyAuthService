package tw.hyin.demo.config.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;

import tw.hyin.demo.config.KeyConfig;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.hyin.java.utils.Log;
import tw.hyin.java.utils.http.ResponseObj;
import tw.hyin.java.utils.security.JwtPayload;
import tw.hyin.java.utils.security.JwtUtil;

/**
 * 攔截所有需要驗證 token 的請求，調用 JwtTokenUtil 方法做 token 驗證
 *
 * @author H-yin
 */
public class JwtTokenFilter  extends OncePerRequestFilter {

    /**
     * 驗證請求
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        try {
            //是否包含請求 header
            String header = req.getHeader(HttpHeaders.AUTHORIZATION);
            //header 是否包含前綴字
            if (header == null || !header.startsWith(JwtUtil.PREFIX)) {
                chain.doFilter(req, res);
                unsuccessfulResponse(res, new Exception("token validation error."));
            } else {
                //獲取權限
                UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
                //將 Authentication 寫入 SecurityContextHolder 以便後續使用
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(req, res);
            }
        } catch (Exception e) {
            unsuccessfulResponse(res, e);
            e.printStackTrace();
        }
    }

    /**
     * 取得驗證訊息
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws Exception {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            //使用 RSA 公鑰解析 token 取得 payload (userName)
            JwtPayload<String> payload = JwtUtil.getPayload(token, KeyConfig.publicKey, String.class);
            if (payload != null) {
                return new UsernamePasswordAuthenticationToken(payload.getCredentials(), payload, null);
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * token 驗證失敗
     */
    @SneakyThrows
    private void unsuccessfulResponse(HttpServletResponse res, Exception e) {
        // 返回錯誤 response
        Log.error(">> Token verify Unsuccessfully: " + e.getMessage());
        List<String> errors = new ArrayList<>();
        errors.add("Token verify Unsuccessfully.");
        errors.add("Unsuccessful Message: " + e.getMessage());
        ResponseObj<Object> responseObj = new ResponseObj<>(HttpStatus.UNAUTHORIZED, errors, null);
        res.setContentType("application/json");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getOutputStream().println(new ObjectMapper().writeValueAsString(responseObj));
    }
}