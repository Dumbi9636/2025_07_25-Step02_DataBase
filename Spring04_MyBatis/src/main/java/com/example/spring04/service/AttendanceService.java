package com.example.spring04.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    // ✅ 출근 기록 (임시 메모리 저장용)
    private final Map<String, List<String>> checkInHistory = new HashMap<>();

    // ✅ 퇴근 기록 (임시 메모리 저장용)
    private final Map<String, List<String>> checkOutHistory = new HashMap<>();

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * ✅ 출근 기록 메서드
     */
    public String recordCheckIn(String empId) {
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(fmt);

        System.out.println("[출근등록] " + empId + " / 시간: " + time);

        checkInHistory.putIfAbsent(empId, new ArrayList<>());
        checkInHistory.get(empId).add(time);

        return time;
    }

    /**
     * ✅ 퇴근 기록 메서드
     */
    public String recordCheckOut(String empId) {
        LocalDateTime now = LocalDateTime.now();
        String time = now.format(fmt);

        System.out.println("[퇴근등록] " + empId + " / 시간: " + time);

        checkOutHistory.putIfAbsent(empId, new ArrayList<>());
        checkOutHistory.get(empId).add(time);

        return time;
    }

    /**
     * ✅ 최근 출근 기록 조회
     */
    public List<String> getRecentCheckIns(String empId) {
        return checkInHistory.getOrDefault(empId, List.of());
    }

    /**
     * ✅ 최근 퇴근 기록 조회
     */
    public List<String> getRecentCheckOuts(String empId) {
        return checkOutHistory.getOrDefault(empId, List.of());
    }

    /**
     * ✅ QR 스캔 시 통합 처리
     */
    public String processAttendance(String empId, String type) {
        if ("checkin".equalsIgnoreCase(type)) {
            return recordCheckIn(empId);
        } else if ("checkout".equalsIgnoreCase(type)) {
            return recordCheckOut(empId);
        } else {
            throw new IllegalArgumentException("잘못된 타입: " + type);
        }
    }
}
