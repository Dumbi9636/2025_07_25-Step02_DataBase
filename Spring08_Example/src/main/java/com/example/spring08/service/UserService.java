package com.example.spring08.service;

import java.util.Map;

import com.example.spring08.dto.PwdChangeRequest;
import com.example.spring08.dto.UserDto;

public interface UserService {
	
	public void createUser(UserDto dto);
	
	public UserDto getUser(String userName);
	
	public void updatePassword(PwdChangeRequest pcr);
	
	public Map<String, Object> canUseId(String id);
	
	public void updateUser(UserDto dto);
	
	
	
}
