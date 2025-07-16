package test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member")
public class MemberServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get 방식 요청 파라미터 추출  "/member?num=x"
		// 추출과 동시에 문자열로 리턴된 숫자를 실제 정수로 바꾸기 
		int num = Integer.parseInt(request.getParameter("num"));
		// 1 번 회원의 정보를 DB 에서 읽어와서 응답한다고 가정하자
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
		pw.println("<title>회원 정보 자세히 보기페이지</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<p>"+num+"번 회원의 정보는 다음과 같습니다. 이름:xxx , 주소:xxx</p>");
		pw.println("</body>");
		pw.println("</html>");
		pw.close();
	}
}













