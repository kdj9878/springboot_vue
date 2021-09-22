package com.xxx.boot.config;



import com.xxx.boot.jwt.TokenProvider;
import com.xxx.boot.jwt.JwtAuthenticationEntryPont;
import com.xxx.boot.jwt.JwtAccessDeniedHandler;
import com.xxx.boot.jwt.JwtSecurityConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //PreAuthorize 어노테이션을 메소드단위로 사용하기 위해서 사용
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPont authenticationEntryPont;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(
        TokenProvider tokenProvider,
        JwtAuthenticationEntryPont authenticationEntryPont,
        JwtAccessDeniedHandler accessDeniedHandler){
        
        this.tokenProvider = tokenProvider;
        this.authenticationEntryPont = authenticationEntryPont;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        //password를 인코딩하는 메소드
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web .ignoring()
            .antMatchers("/favicon.ico");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()   //csrf : 자신의 의지와는 무관하게 특정 웹사이트에(수정, 삭제, 등록 등) 요청하는 행위
                                //토큰 방식이기 때문에 설정을 안함

            .exceptionHandling()    //exceptin을 핸들링 할 때 각각의 상황이 발생하면 해당 메소드를 실행
            .authenticationEntryPoint(authenticationEntryPont)
            .accessDeniedHandler(accessDeniedHandler)

            .and()
            .sessionManagement()    //세션을 사용하지 않기 때문에 세션 설정을 stateless로
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()    //HttpServletRequest를 사용하는 요청들에 대한 접근 제한
            .antMatchers("/**").permitAll()  //해당 주소의 요청은 인증없이 접근을 허용하겠다는 의미
            .antMatchers("/manager/**").authenticated()  //해당들에 대해서는 모두 인증을 받아야 한다는 의미 
            .antMatchers("/admin/**").authenticated()

            .and()
            .apply(new JwtSecurityConfig(tokenProvider));
    }

}
