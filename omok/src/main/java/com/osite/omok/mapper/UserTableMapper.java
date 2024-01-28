package com.osite.omok.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.osite.omok.entity.UserTable;

@Mapper
public interface UserTableMapper {
	
	UserTable findByUserNum(Integer userNum);

}
