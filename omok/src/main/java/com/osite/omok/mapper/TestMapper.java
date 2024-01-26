package com.osite.omok.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.osite.omok.entity.UserTable;

@Mapper
public interface TestMapper {
	List<UserTable> testByID();
}
