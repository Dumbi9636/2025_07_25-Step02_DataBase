package com.example.spring08.repository;

import com.example.spring08.dto.UserDto;

public interface UserDao {
	public void insert(UserDto dto);
	public int update(UserDto dto);
	public int updatePassword(UserDto dto);
	public UserDto getByNum(long num);
	public UserDto getByUserName(String userName);
	
}
