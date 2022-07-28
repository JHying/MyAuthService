package tw.hyin.demo.config.web;

import java.util.Map;

import lombok.SneakyThrows;
import tw.hyin.demo.config.KeyConfig;
import tw.hyin.demo.dto.JwtUser;
import tw.hyin.demo.dto.TokenReq;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import tw.hyin.java.utils.security.RSAUtil;

/**
 * 初次登入使用 >> 自定義認證方法
 *
 * @author H-yin
 */
public class JwtLoginProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //獲取欲認證之帳密
        TokenReq tokenReq = (TokenReq) authentication.getCredentials();
        //認證結果
        if (tokenReq != null && isValidated(tokenReq)) {
            //生成驗證狀態，導向驗證成功
            return authentication;
        } else {
            //驗證失敗
            throw new BadCredentialsException(authentication.getPrincipal().toString() + " > get token failed.");
        }
    }

    @SneakyThrows
    private boolean isValidated(TokenReq tokenReq) {
        boolean check = false;
        Map<String, String> user = JwtUser.getUser();
        //前端使用公鑰加密訊息傳送，後端使用私鑰解密
        String decryptPW = RSAUtil.decrypt(tokenReq.getJwtPW().getBytes(), KeyConfig.privateKey);
        //驗證 jwt user
        for (Map.Entry<String, String> entry : user.entrySet()) {
            if (tokenReq.getJwtUser().equals(entry.getKey()) && decryptPW.equals(entry.getValue())) {
                check = true;
                break;
            }
        }
        return check;
    }

    // 是否可以提供輸入類型的認證服務
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
