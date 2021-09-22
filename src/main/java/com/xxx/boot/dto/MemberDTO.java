package com.xxx.boot.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO implements Serializable{
    private int member_id;
    private String member_name;
    private String member_pw;
    private String member_email;
    private String nickName;
    private int activated;

    private String authority_name;







}
