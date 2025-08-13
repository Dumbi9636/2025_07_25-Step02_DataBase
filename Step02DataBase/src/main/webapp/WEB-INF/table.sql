CREATE TABLE gallery(
	num NUMBER PRIMARY KEY,
	title VARCHAR2(100) NOT NULL,
	writer VARCHAR2(20) NOT NULL,
	content CLOB,
	createdAt DATE DEFAULT SYSDATE
);
-- gallery 테이블은 board_seq를 공유하기

CREATE TABLE gallery_image(
	num NUMBER PRIMARY KEY,
	gallery_num NUMBER REFERENCES gallery(num),
	saveFileName VARCHAR2(100) NOT NULL, -- 사진을 보여주는 용도로만 사용할 예정(저장된 파일명만 있으면 된다 다운로드 추가해주려면 orgFileName, FileSize도 추가해줘야함)
	createdAt DATE DEFAULT SYSDATE
);

CREATE SEQUENCE gallery_image_seq;


SELECT * FROM comments;

CREATE TABLE comments(
	num NUMBER PRIMARY KEY, -- 댓글의 글번호
	writer VARCHAR2(20) NOT NULL, -- 작성자
	content VARCHAR2(100) NOT NULL, -- 내용
	targetWriter VARCHAR2(20) NOT NULL, -- 누구에게 작성한 댓글인지 
	groupNum NUMBER NOT NULL, -- 댓글의 그룹번호
	parentNum NUMBER NOT NULL, -- 부모가 되는 원글의 글번호
	deleted CHAR(3) DEFAULT 'no', --댓글을 삭제했는지 여부 
	createdAt DATE DEFAULT SYSDATE -- 댓글 작성일
);

CREATE SEQUENCE comments_seq;

CREATE TABLE board(
	num NUMBER PRIMARY KEY,
	writer VARCHAR2(20) NOT NULL,
	title VARCHAR2(50) NOT NULL,
	content CLOB,
	viewCount NUMBER DEFAULT 0,
	createdAt DATE DEFAULT SYSDATE
);

CREATE SEQUENCE board_seq;

CREATE TABLE users(
	num NUMBER PRIMARY KEY, -- 회원의 고유번호
	userName VARCHAR2(20) UNIQUE, -- 아이디
	password VARCHAR2(100) NOT NULL, -- 비밀번호
	email VARCHAR2(50) UNIQUE, -- 이메일
	profileImage VARCHAR2(100), -- 프로필 이미지 정보(처음 가입시 null)
	role VARCHAR2(10), DEFAULT 'ROLE_USER', -- 역할 ROLE_USER(일반사용자) | ROLE_STAFF(직원) | ROLE_ADMIN(최고권한관리자)
	updateAt DATE, -- 수정 날짜
	createAt DATE -- 가입 날짜
);


CREATE TABLE member(
	num NUMBER PRIMARY KEY,
	name VARCHAR2(20),
	addr VARCHAR2(20)
);

CREATE SEQUENCE member_seq;

CREATE TABLE book(
	num NUMBER PRIMARY KEY,
	title VARCHAR2(20),
	author VARCHAR2(20),
	publisher VARCHAR2(20)
);

CREATE SEQUENCE book_seq;