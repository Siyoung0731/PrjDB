계정 sky


--  TMEM
-- 회원관리
-- 번호 숫자(6) 기본키 자동증가
-- 이름 문자(30) 필수입력
-- 아이디 문자(20) 필수입력 중복 방지
-- 암호 문자(20) 필수입력
-- 이메일 문자(320) 중복방지
-- 가입일 날짜 기본값 오늘
-- 
-- 아이디 문자(20) 필수입력 중복방지
-- 이름 문자(30) 필수입력
-- 이메일 문자(320) 중복방지

CREATE TABLE TMEM (
    USERID VARCHAR2(20) NOT NULL PRIMARY KEY
    ,USERNAME VARCHAR2(30) NOT NULL
    ,EMAIL VARCHAR2(320) UNIQUE  
);

INSERT INTO TMEM(USERID, USERNAME, EMAIL) VALUES('aaaa', 'sana', 'aaaa@green.com');
INSERT INTO TMEM VALUES('bbbb', 'kina', 'bbbb@green.com');
INSERT INTO TMEM VALUES('cccc', 'wori', 'cccc@green.com');
INSERT INTO TMEM VALUES('dddd', 'lena', 'dddd@green.com');
INSERT INTO TMEM VALUES('eeee', 'rina', 'eeee@green.com');
commit;