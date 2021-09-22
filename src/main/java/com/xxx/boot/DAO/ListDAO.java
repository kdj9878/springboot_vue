package com.xxx.boot.DAO;

import java.util.List;

import com.xxx.boot.mapperInterface.ListMapper;
import com.xxx.boot.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListDAO {
    
    @Autowired
    private ListMapper listMapper;

    public List<ListDTO> getList(){

        return listMapper.AllList();
    }
}
