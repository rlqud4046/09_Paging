-- zipcode 테이블 만들기

create table zipcode(
	no varchar2(5) primary key,   -- 레코드 번호
	zipcode varchar2(7),          -- 우편번호
	sido varchar2(10),            -- 시,도
	gugun varchar2(20),           -- 구,군
	dong varchar2(50),            -- 동
	bunji varchar2(50)            -- 번지
);