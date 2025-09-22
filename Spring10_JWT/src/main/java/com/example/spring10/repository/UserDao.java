package com.example.spring10.repository;

import com.example.spring10.dto.UserDto;

public interface UserDao {
	public void insert(UserDto dto);
	public int update(UserDto dto);
	public int updatePassword(UserDto dto);
	public UserDto getByNum(long num);
	public UserDto getByUserName(String userName);
	
}
