<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/jstl/test02.jsp</title>
</head>
<body>
	<c:if test="true">
		<p>이 문장이 출력될까?</p>
	</c:if>
	<c:if test="false">
		<p>이 문장이 안되나?</p>
	</c:if>
	<%-- el 을 이용해서 연산의 결과를 jstl 이 사용하도록 한다 --%>
	<c:if test="${10%2 == 0 }">
		<p>10 은 짝수 입니다라는 속성값은 true 입니다</p>
	</c:if>
	<c:if test="${10%2 eq 0 }"> <%-- 비교 연산자 eq 는 == 와 같다(equal) --%>
		<p>10 은 짝수 입니다라는 속성값은 true 입니다</p>
	</c:if>
	<c:if test="${20 gt 10}"> <%-- 비교 연산자 gt 는 > 와 같다(greater than) --%>
		<p>20 은 10 보다 크다라는 속성값은 true 입니다</p>
	</c:if>
	<c:if test="${10 != 20 }">
		<p>10 과 20 은 다르다 ${10 != 20 }</p> <%-- ne 는 !=와 같다 (not equal) --%>
	</c:if>
	<c:if test="${'kim' != 'park' }"> <%-- el 에서 문자열은 싱글따옴표로 감싼다 --%>
		<p>kim 과 park 은 다르다</p>
	</c:if>
</body>
</html>