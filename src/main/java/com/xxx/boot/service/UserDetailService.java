package com.xxx.boot.service;

import com.xxx.boot.DAO.UserDAO;
import com.xxx.boot.security.CostomUserDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService{
    
    @Autowired
    private UserDAO userDao;

    @Override
    public CostomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        CostomUserDetail user = userDao.getUserById(username);
        if (user == null) {
            throw new UsernameNotFoundException("memberName " + username + "not found");
        }
        return user;
    }

    
}
