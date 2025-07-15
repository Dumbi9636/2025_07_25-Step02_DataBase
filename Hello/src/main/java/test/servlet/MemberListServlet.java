package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import test.dao.MemberDao;
import test.dto.MemberDto;

@WebServlet("/members")
public class MemberListServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//회원목록을 DB 에서 select 해온다.
		List<MemberDto> list=new MemberDao().selectAll();
		
		//응답 인코딩 설정
		response.setCharacterEncoding("utf-8");
		//응답 컨텐츠 설정
		response.setContentType("text/html; charset=utf-8");

		//클라이언트에게 출력할수 있는 객체의 참조값 얻어내기
		PrintWriter pw = response.getWriter();
		pw.println("<!doctype html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset='utf-8'>");
		pw.println("<title>회원 목록 페이지</title>");
		pw.println("</head>");
		pw.println("<body>");
		
		for(int i=0; i<5; i++) {
			pw.println("<p>hello"+i+"</p>");
		}
		
		pw.println("<h3>회원 목록</h3>");
		pw.println("<table border='1' cellpadding='8' cellspacing='0'>");
			pw.println("<thead>");
				pw.println("<tr>");
					pw.println("<th>번호</th>");
					pw.println("<th>이름</th>");
					pw.println("<th>주소</th>");
				pw.println("</tr>");
			pw.println("</thead>");
			pw.println("<tbody>");
			//반복문 돌면서 List<MemberDto> 객체 안에 있는 모든 회원정보를 tr 로 구성해서 출력해 준다.
			for(MemberDto tmp:list) {
				pw.println("<tr>");
					pw.println("<td>"+tmp.getNum()+"</td>");
					pw.println("<td>"+tmp.getName()+"</td>");
					pw.println("<td>"+tmp.getAddr()+"</td>");
				pw.println("</tr>");
			}
			pw.println("</tbody>");
		pw.println("</table>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}






















