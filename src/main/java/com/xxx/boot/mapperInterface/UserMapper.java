package com.xxx.boot.mapperInterface;

import com.xxx.boot.security.CostomUserDetail;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    // public을 안 붙여주면 찾지를 못해서 500에러 발생...이것 땜시..하아
    public CostomUserDetail getUserById(String username);
    
}
