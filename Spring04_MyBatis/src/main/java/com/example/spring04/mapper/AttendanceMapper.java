package com.example.spring04.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AttendanceMapper {

    // ✅ 출근 시 INSERT
    void insertCheckIn(@Param("empId") String empId);

    // ✅ 퇴근 시 UPDATE
    void updateCheckOut(@Param("empId") String empId);
}
