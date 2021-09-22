package com.xxx.boot.mapperInterface;

import java.util.List;

import com.xxx.boot.dto.ListDTO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ListMapper {


    List<ListDTO> AllList();

    

}
