package tw.hyin.demo.config.web;

import tw.hyin.demo.repo._UserTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 設置 Spring Security 規則
 *
 * @author H-yin
 */

/*
 * @EnableWebSecurity: 啟動 spring security 配置 = @Import({
 * WebSecurityConfiguration.class, SpringWebMvcImportSelector.class })
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private _UserTokenRepository tokenRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()// 關閉csrf驗證
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// jwt 可實現無狀態，不需要 session
                // 定義驗證規則
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()// 全部都要認證
                .and()
                // 取得 token 之請求 (該 url) 交給 JwtLoginFilter 來處理 (要先登入才能取得token)
                .addFilterBefore(new JwtLoginFilter("/register", authenticationManager(), tokenRepository),
                        UsernamePasswordAuthenticationFilter.class)
                // 自定義過濾器驗證 Token 是否合法
                .addFilterBefore(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO Auto-generated method stub
        // 帳戶驗證使用自定義類別
        auth.authenticationProvider(new JwtLoginProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
//                .anyRequest();
                .antMatchers(HttpMethod.OPTIONS, "/**")
                // 其他API
                .antMatchers("/login", "/logout/*", "/test", "/sidenav", "/authlist/get");
    }

}
