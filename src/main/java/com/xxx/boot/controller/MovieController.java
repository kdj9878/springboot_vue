package com.xxx.boot.controller;

import java.util.List;

import com.xxx.boot.DAO.ListDAO;
import com.xxx.boot.dto.ListDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "movie")
public class MovieController {
    
    @Autowired
    private ListDAO listDao;

    @GetMapping("/getList")
    public List<ListDTO> showList(){
        
        return listDao.getList();
    }


}
