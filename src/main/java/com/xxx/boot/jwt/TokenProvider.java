package com.xxx.boot.jwt;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


// 토큰의 생성, 토큰의 유효성 검증 등을 담당할 클래스
@PropertySource("classpath:/application.properties")
@Component  //빈을 생성
public class TokenProvider implements InitializingBean{

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITiES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInMilliseconds;

    private Key key;

    //빈을 주입 받음
    public TokenProvider(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds){
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //secret값을 base64로 디코딩
        byte[] keyByes = Decoders.BASE64.decode(secret);
        //디코딩한 값을 key변수에 할당
        this.key = Keys.hmacShaKeyFor(keyByes);
        
    }

    // public String getUsernameFromToekn(String token){
    //     return getUsernameFromToekn(token, Claims::getSubject);
    // }

    //Authoentication객체의 권한정보를 이용해서 토큰을 생성
    public String createToken(Authentication authentication){
        // Authentication 인터페이스 : 인증 정보를 의미하는 인터페이스
        // getAuthorities() : 계정이 가지고 있는 권한 목록을 리턴
        // stream() : 배열 또는 컬렉션 인스턴스에 함수 여러개를 조합해서 원하는 결과를
        // 필터링한다.
        // collect() : 요소들을 필터링/매핑한 후 요소들을 수집하는 최종처리 메소드
        //             필요한 요소만 컬렉션으로 담을 수 있고, 요소들을 그룹화 한 후 집계할 수 있음
        // Collectors.joining() : StringBuilder를 생성한 후 계속 추가해준 후 반환
        // GrantedAuthority : ID, Password기반 인증에서 UserDetailsService를 통해서 조회된다.

        String authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority :: getAuthority)
                    //stream 내 요소에 각각 GrantedAuthority의 getAuthority를 실행

                    .collect(Collectors.joining(","));
                    //StringBuilder를 생성한 후 , 를 추가해준 후 반환

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
        //현재 시간에 application.properties에서 설정한 토큰 만료시간을 더해서 유효시간을 설정함

        return Jwts.builder()
                .setSubject(authentication.getName()) //토큰 제목, 여기서는 User 이름
                .claim(AUTHORITiES_KEY, authorities) // key : auto value : 권한들
                .signWith(key, SignatureAlgorithm.HS512)    //디코딩 한 값을 HS512알고리즘으로 암호화
                .setExpiration(validity)    //토큰 만료시간을 세팅
                .compact(); //압축
    }   

    // 토큰 검증, String 자료형으로 온 token을 사용하기 위한 형태로 parsing하기 위해서 jwtsparse를 사용
    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parserBuilder()    //jwtParserBuilder 인스턴스 생성
                .setSigningKey(key) //암호화 된 key변수를 세팅
                .build()            //스레드에 안전하게 리턴하게 위해서 호출(왜...?)
                .parseClaimsJws(token)  //토큰을 jws로 파싱
                .getBody(); //토큰에 저장했던 data들이 담긴 claims를 가져 옴
        
        Collection<? extends GrantedAuthority> authorities = 
                Arrays.stream(claims.get(AUTHORITiES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());  
        
        User principal = new User(claims.getSubject(), "", authorities); // 권한들을 이용해서 유저 객체를 생성

        //유저 객체, 토큰, 권한들을 담은 Authentication 객체를 리턴
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);  
    }

    //토큰의 유효성 검증을 하는 메소드
    public boolean validateToken(String token){
        try {
            //토큰을 파싱했을 때 성공하면 true를 리턴
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e){
            logger.info("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e){
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e){
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
