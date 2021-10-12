package com.xxx.boot.controller;

import com.xxx.boot.dto.TokenDTO;
import com.xxx.boot.jwt.TokenProvider;
import com.xxx.boot.security.CostomUserDetail;
import com.xxx.boot.service.UserDetailService;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/user")
public class loginController {

    private static final Logger logger = LoggerFactory.getLogger(loginController.class);
    

    private TokenProvider tokenProvider;
    private UserDetailService userDetailService;
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public loginController(
        TokenProvider tokenProvider,
        UserDetailService userDetailService,
        AuthenticationManagerBuilder authenticationManagerBuilder
    ){
        this.tokenProvider = tokenProvider;
        this.userDetailService = userDetailService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody String userInfo) throws ParseException{
        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(userInfo);

        String username = jo.get("username").toString();
        String password = jo.get("password").toString();

        CostomUserDetail costomUser = userDetailService.loadUserByUsername(username);

        logger.info("username : " + costomUser.getUsername());
        logger.info("password : " + costomUser.getPassword());
        
        if (username == costomUser.getUsername()) {
            if (password == costomUser.getPassword()) {
                
                UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

                    Authentication authentication =
                    authenticationManagerBuilder.getObject().authenticate(authenticationToken);

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    String accessToken = tokenProvider.createToken(authentication);

                    TokenDTO token = new TokenDTO(accessToken, null);
                    System.out.println("여기 오나");
                    return new ResponseEntity<Object>(token, HttpStatus.OK);
                }
                else{
                    logger.error("비밀번호가 일치하지 않습니다.");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
        }   
        else{
            logger.error("존재하지 않는 아이디입니다.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }
}
