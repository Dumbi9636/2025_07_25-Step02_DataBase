package test.servlet;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test/downLoad")
public class TestDownloadServlet extends HttpServlet{
	// 이미지 저장 경로 
	// 원래 어디에 저장되었는지
    private  String fileLocation;
    
    @Override
    public void init() throws ServletException {
        ServletContext context = getServletContext();
        fileLocation = context.getInitParameter("fileLocation");
    }
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get 방식 파라미터로 전달되는 다운로드해줄 파일의 정보를 얻어온다(원래는 pk 를 이용해서 DB 에서 읽어와야함)
		String orgFileName=request.getParameter("orgFileName");
		String saveFileName=request.getParameter("saveFileName");
		//여기에서는 굳이 long type 은 필요 없지만 테스트로 바꿔봄
		long fileSize = Long.parseLong(request.getParameter("fileSize"));
		//응답 헤더 정보 설정
		response.setHeader("Content-Type", "application/octet-stream; charset=UTF-8");
		//다운로드 시켜줄 파일명 인코딩  
			String encodedName=URLEncoder.encode(orgFileName, "utf-8");
		//파일명에 공백이있는 경우 처리 
		encodedName=encodedName.replaceAll("\\+"," ");
		
		response.setHeader("Content-Disposition", "attachment;filename="+encodedName);
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		//다운로드할 파일의 크기
		response.setContentLengthLong(fileSize);
		
		//다운로드 시켜줄 파일의 실제 경로
		String path=fileLocation+File.separator+saveFileName;
		
		FileInputStream fis=null;
		BufferedOutputStream bos=null;
		try {
			//파일에서 byte 을 읽어들일 객체
			fis=new FileInputStream(path);
			//클라이언트에게 출력할수 있는 스트림 객체 얻어오기
			bos=new BufferedOutputStream(response.getOutputStream());
			//한번에 최대 1M byte 씩 읽어올수 있는 버퍼
		   	byte[] buffer=new byte[1024*1024];
		   	int readedByte=0;
		   	//반복문 돌면서 출력해주기
		   	while(true) {
		   		//byte[] 객체를 이용해서 파일에서 byte 알갱이 읽어오기
		   		readedByte = fis.read(buffer);
		   		if(readedByte == -1)break; //더이상 읽을 데이터가 없다면 반복문 빠져 나오기
		   		//읽은 만큼 출력하기
		   		bos.write(buffer, 0, readedByte);
		   		bos.flush(); //출력
		   	}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(fis!=null)fis.close();
			if(bos!=null)bos.close();
		}		
	}
}
