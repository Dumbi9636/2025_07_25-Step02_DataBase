package com.example.spring10.service;

import java.util.Map;

import com.example.spring10.dto.PwdChangeRequest;
import com.example.spring10.dto.UserDto;

public interface UserService {
	
	public void createUser(UserDto dto);
	
	public UserDto getUser(String userName);
	
	public void updatePassword(PwdChangeRequest pcr);
	
	public Map<String, Object> canUseId(String id);
	
	public void updateUser(UserDto dto);
	
	
	
}
