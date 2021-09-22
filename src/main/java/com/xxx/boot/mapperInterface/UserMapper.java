package com.xxx.boot.mapperInterface;

import com.xxx.boot.security.CostomUserDetail;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {


    CostomUserDetail getUserById(String member_name);
    
}
