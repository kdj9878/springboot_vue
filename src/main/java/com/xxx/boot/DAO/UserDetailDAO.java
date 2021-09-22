package com.xxx.boot.DAO;

import com.xxx.boot.security.CostomUserDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailDAO implements UserDetailsService{
    
    @Autowired
    private UserDAO userDao;

    @Override
    public UserDetails loadUserByUsername(String member_name) throws UsernameNotFoundException {
        CostomUserDetail user = userDao.getUserById(member_name);
        if (user == null) {
            throw new UsernameNotFoundException("memberName " + member_name + "not found");
        }
        System.out.println("***********Found User**************");
        System.out.println("id : " + member_name);
        return user;
    }

    
}
