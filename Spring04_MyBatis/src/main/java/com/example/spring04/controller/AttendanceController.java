package com.example.spring04.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.spring04.service.AttendanceService;
import com.example.spring04.util.JwtUtil;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/v1/attendance")
public class AttendanceController {

    private final JwtUtil jwtUtil;
    private final AttendanceService attendanceService;

    public AttendanceController(JwtUtil jwtUtil, AttendanceService attendanceService) {
        this.jwtUtil = jwtUtil;
        this.attendanceService = attendanceService;
    }

    @PostConstruct
    public void init() {
        System.out.println("✅ AttendanceController Loaded!!");
    }

    // ✅ QR 토큰 발급 (출근 / 퇴근 공용)
    @GetMapping("/qr-token")
    public Map<String, String> getQrToken(@RequestParam String empId,
                                          @RequestParam(defaultValue = "checkin") String type) {
        System.out.println("✅ /v1/attendance/qr-token 요청: empId=" + empId + ", type=" + type);
        String token = jwtUtil.createToken(empId, type, 60);
        return Map.of("qrToken", token, "type", type);
    }
    
    
    @GetMapping("/qr-scan")
    public ResponseEntity<String> scanQrGet(@RequestParam String token) {
        try {
            Map<String, String> data = jwtUtil.validateToken(token);
            String empId = data.get("empId");
            String type = data.get("type");

            String recordedTime = attendanceService.processAttendance(empId, type);
            String message = type.equals("checkin") ? "출근 등록 완료 ✅" : "퇴근 등록 완료 ✅";

            // ✅ HTML로 결과 보여주기
            String html = """
                <!DOCTYPE html>
                <html>
                  <head><meta charset='UTF-8'><title>QR 등록 결과</title></head>
                  <body style='font-family:sans-serif;text-align:center;margin-top:60px;'>
                    <h2>%s</h2>
                    <p>직원 ID: %s</p>
                    <p>등록 시간: %s</p>
                    <button onclick="window.location.href='/v1/attendance/history?empId=%s'"
                            style='padding:10px 20px;border:none;border-radius:8px;background:#4CAF50;color:white;'>출퇴근 기록 보기</button>
                  </body>
                </html>
            """.formatted(message, empId, recordedTime, empId);

            return ResponseEntity.ok()
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body(html);
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body("<h3>❌ QR 코드가 만료되었거나 유효하지 않습니다.</h3>");
        }
    }

    // ✅ QR 스캔 API (React에서 스캔 시 호출)
    @PostMapping("/qr-scan")
    public ResponseEntity<Map<String, String>> scanQr(@RequestParam String token) {
        try {
            Map<String, String> data = jwtUtil.validateToken(token);
            String empId = data.get("empId");
            String type = data.get("type");

            String recordedTime = attendanceService.processAttendance(empId, type);
            String message = type.equals("checkin") ? "출근 등록되었습니다." : "퇴근 등록되었습니다.";

            return ResponseEntity.ok(Map.of(
                "message", message,
                "empId", empId,
                "time", recordedTime
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "❌ QR 코드가 유효하지 않습니다."));
        }
    }

    // ✅ [출근용] QR 클릭 시 브라우저에서 직접 접근하는 경우
    @GetMapping("/checkin")
    public ResponseEntity<String> checkIn(@RequestParam String token) {
        System.out.println("📩 /v1/attendance/checkin 요청");

        if (!jwtUtil.isValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body("<h2 style='color:red;text-align:center;'>QR 코드가 만료되었거나 위조되었습니다 ❌</h2>");
        }

        String empId = jwtUtil.getEmpId(token);
        String checkInTime = attendanceService.recordCheckIn(empId);

        String html = """
            <!DOCTYPE html><html><head><meta charset='UTF-8'><title>출근 등록</title></head>
            <body style='font-family:sans-serif;text-align:center;margin-top:60px;'>
              <div id='step1'><h2>출근 등록되었습니다 ✅</h2></div>
              <div id='step2' style='display:none;'>
                <h2>%s님 출근 등록 완료 ✅</h2>
                <p style='font-size:18px;color:#555;'>출근 시간 : %s</p>
                <button onclick="window.location.href='/v1/attendance/history?empId=%s'"
                  style='padding:10px 20px;font-size:16px;margin-top:20px;border:none;border-radius:8px;background:#4CAF50;color:white;cursor:pointer;'>출근 기록 보기</button>
              </div>
              <script>
                setTimeout(() => {
                  document.getElementById('step1').style.display='none';
                  document.getElementById('step2').style.display='block';
                }, 5000);
              </script>
            </body></html>
            """.formatted(empId, checkInTime, empId);

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html);
    }

    // ✅ [퇴근용] QR 클릭 시 브라우저에서 직접 접근하는 경우
    @GetMapping("/checkout")
    public ResponseEntity<String> checkOut(@RequestParam String token) {
        System.out.println("📩 /v1/attendance/checkout 요청");

        if (!jwtUtil.isValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header("Content-Type", "text/html; charset=UTF-8")
                    .body("<h2 style='color:red;text-align:center;'>QR 코드가 만료되었거나 위조되었습니다 ❌</h2>");
        }

        String empId = jwtUtil.getEmpId(token);
        String checkOutTime = attendanceService.recordCheckOut(empId);

        String html = """
            <!DOCTYPE html><html><head><meta charset='UTF-8'><title>퇴근 등록</title></head>
            <body style='font-family:sans-serif;text-align:center;margin-top:60px;'>
              <div id='step1'><h2>퇴근 등록되었습니다 ✅</h2></div>
              <div id='step2' style='display:none;'>
                <h2>%s님 퇴근 등록 완료 ✅</h2>
                <p style='font-size:18px;color:#555;'>퇴근 시간 : %s</p>
                <button onclick="window.location.href='/v1/attendance/history?empId=%s'"
                  style='padding:10px 20px;font-size:16px;margin-top:20px;border:none;border-radius:8px;background:#4CAF50;color:white;cursor:pointer;'>출퇴근 기록 보기</button>
              </div>
              <script>
                setTimeout(() => {
                  document.getElementById('step1').style.display='none';
                  document.getElementById('step2').style.display='block';
                }, 5000);
              </script>
            </body></html>
            """.formatted(empId, checkOutTime, empId);

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html);
    }

    // ✅ 출근 내역 보기
    @GetMapping("/history")
    public ResponseEntity<String> showHistory(@RequestParam String empId) {
        List<String> checkIns = attendanceService.getRecentCheckIns(empId);
        List<String> checkOuts = attendanceService.getRecentCheckOuts(empId);

        StringBuilder html = new StringBuilder();
        html.append("<div style='text-align:center;margin-top:50px;font-family:sans-serif;'>");
        html.append("<h2>").append(empId).append("님의 출퇴근 내역</h2>");
        html.append("<table style='margin:20px auto;border-collapse:collapse;'>");
        html.append("<tr><th style='border:1px solid #ccc;padding:8px;'>번호</th>")
            .append("<th style='border:1px solid #ccc;padding:8px;'>출근 시간</th>")
            .append("<th style='border:1px solid #ccc;padding:8px;'>퇴근 시간</th></tr>");

        int rows = Math.max(checkIns.size(), checkOuts.size());
        for (int i = 0; i < rows; i++) {
            html.append("<tr>")
                .append("<td style='border:1px solid #ccc;padding:8px;'>").append(i + 1).append("</td>")
                .append("<td style='border:1px solid #ccc;padding:8px;'>")
                .append(i < checkIns.size() ? checkIns.get(i) : "-").append("</td>")
                .append("<td style='border:1px solid #ccc;padding:8px;'>")
                .append(i < checkOuts.size() ? checkOuts.get(i) : "-").append("</td>")
                .append("</tr>");
        }

        html.append("</table>");
        html.append("<button onclick=\"history.back()\" style='padding:10px 20px;font-size:16px;border:none;border-radius:8px;background:#555;color:white;cursor:pointer;'>뒤로가기</button>");
        html.append("</div>");

        return ResponseEntity.ok()
                .header("Content-Type", "text/html; charset=UTF-8")
                .body(html.toString());
    }
}
