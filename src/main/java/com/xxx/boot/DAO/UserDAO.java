package com.xxx.boot.DAO;

import com.xxx.boot.mapperInterface.UserMapper;
import com.xxx.boot.security.CostomUserDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAO {
    
    @Autowired
    private UserMapper usermapper;

    public CostomUserDetail getUserById(String username) {
        return usermapper.getUserById(username);
    }


}
