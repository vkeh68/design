/* 지점 테이블 */
create table branch_office (
	BHF_CODE int AUTO_INCREMENT,	/* 지점 코드 */
	BHF_NM varchar(50) not null,	/* 지점 이름 */
	BHF_ADRES varchar(100) not null,	/* 지점 주소 */
	BHF_TELNO varchar(20) not null,	/* 지점 전화번호 */
	PRIMARY KEY(BHF_CODE)
);

/* 도면 테이블 */
create table drawing (
	DRW_CODE int AUTO_INCREMENT,
	DRW_FLPTH varchar(100) not null,	/* 도면 파일 경로 */
	PRIMARY KEY(DRW_CODE)
);

/* 층 정보 테이블
	지점, 도면 테이블을 참조함
 */

create table floor_information (
	BHF_CODE int not null,
	DRW_CODE int not null,
	FLOORINFO_FLOOR varchar(10) not null,	/* 층 (몇층인지) */
	FLOORINFO_RGSDE date,	/* 등록 날짜 (도면이 해당 층에 등록된 날짜) */
	SIZE_X int not null,
	SIZE_Y int not null,
	PRIMARY KEY(BHF_CODE, DRW_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES branch_office(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DRW_CODE) REFERENCES drawing(DRW_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);


/* 비콘 테이블
	지점 테이블을 참조함
 */
create table beacon (
	BEACON_CODE int AUTO_INCREMENT,
	BEACON_MJR int not null,	/* 비콘 메이저 */
	BEACON_MNR int not null,	/* 비콘 마이너 */
	BEACON_STTUS varchar(10) not null, /* 비콘 사애 */
	BHF_CODE int not null,	/* 지점 코드 */
	PRIMARY KEY(BEACON_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES branch_office(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 타일 테이블 */
create table tile (
	TILE_CODE int AUTO_INCREMENT,
	TILE_NM varchar(10) not null,
	TILE_CRDNT_X int,
	TILE_CRDNT_Y int,
	DRW_CODE int,
	BEACON_CODE int,

	PRIMARY KEY(TILE_CODE),
	FOREIGN KEY(BEACON_CODE) REFERENCES beacon(BEACON_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DRW_CODE) REFERENCES drawing(DRW_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 회원 테이블 */
create table user (
	USER_ID varchar(10) not null,
	USER_PASSWORD varchar(10) not null,
	USER_ADRES varchar(100) not null,
	USER_NM varchar(10) not null,
	USER_BRTHDY varchar(20) not null,		/* 회원 생년월일 */
	USER_MBTLNUM varchar(15) not null,	/* 회원 휴대폰 번호 */
	USER_EMAIL varchar(30) not null,
	USER_SEXDSTN varchar(10) not null,	/* 회원 성별 */
	USER_MRRG_AT varchar(3) not null,	/* 회원 혼인 여부 */
	PRIMARY KEY(USER_ID)
);

/* 경로 테이블
	타일, 사용자 테이블을 참조함
 */
create table course (
	USER_ID varchar(10),
	TILE_CODE int not null,
	COURS_STAY_TIME int, /* 머문 시간 */
	COURS_PASNG_TIME timestamp default now(), /* 지나간 시간*/
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(TILE_CODE) REFERENCES tile(TILE_CODE) ON UPDATE CASCADE ON DELETE CASCADE

);

/* 부서 테이블 */
create table department (
	DEPT_CODE int AUTO_INCREMENT,
	DEPT_NM varchar(10) not null,
	PRIMARY KEY(DEPT_CODE)
);

/* 부서가 속한다 ( 부서 소속 ) 테이블
	지점, 부서 테이블을 참조함
 */
create table department_position (
	DEPT_CODE int not null,
	BHF_CODE int not null,
	DEPTPSITN_ABL_DE date,
	DEPTPSITN_ESTBL_DE date not null, /* 부서 개설 날짜*/
	FOREIGN KEY(DEPT_CODE) REFERENCES department(DEPT_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(BHF_CODE) REFERENCES branch_office(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 직원 테이블
	회원 테이블을 참조함
 */
create table employee (
	USER_ID varchar(10) not null,
	EMP_IHIDNUM varchar(15) not null, /* 직원 주민등록번호 */
	EMP_ANSLRY int,	/* 직원 연봉 */
	EMP_ACNUTNO varchar(20),	/* 직원 계좌번호 */
	EMP_ENCPN date not null,	/* 직원 입사일 */
	EMP_RETIRE date,	/* 직원 퇴사날짜 */
	PRIMARY KEY(USER_ID),
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE
	
);

/* 직원이 소속된다 테이블 ( 직원 소속 )
	회원, 부서 테이블을 참조함
 */
create table employee_position (
	USER_ID varchar(10) not null,
	DEPT_CODE int not null,
	EMPPSITN_RSPOFC varchar(10) not null,	/* 직책 */
	EMPPSITN_GNFD_DE date not null,	/* 발령일자 */
	EMPPSITN_LEAV_DE date,	/* 부서퇴출일자 */
	PRIMARY KEY(USER_ID, DEPT_CODE),
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DEPT_CODE) REFERENCES department(DEPT_CODE) ON UPDATE CASCADE ON DELETE CASCADE
	
);

/* 쿠폰 테이블 */
create table coupon (
	COUPON_CODE int AUTO_INCREMENT,
	COUPON_NM varchar(20) not null,
	COUPON_CNTNTS varchar(500) not null, /* 쿠폰 설명 */
	COUPON_DSCNT varchar(10) not null,	/* 쿠폰 할인율 */
	COUPON_BEGIN_DE date not null,	/* 쿠폰 시작날짜 */
	COUPON_END_DE date,	/* 쿠폰 종료날짜 */
	PRIMARY KEY(COUPON_CODE)
);

/* 쿠폰을 보유할수 있다 ( 쿠폰 보유 ) 테이블
	회원, 쿠폰 테이블을 참조함
 */
create table coupon_hold (
	USER_ID varchar(10) not null,
	COUPON_CODE int not null,
	COUPONHOLD_USE_AT varchar(10) not null, /* 쿠폰 상태 ( 사용여부 ) */
	PRIMARY KEY(USER_ID, COUPON_CODE),
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(COUPON_CODE) REFERENCES coupon(COUPON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 계산서 테이블
	회원 테이블을 참조함
 */
create table bill (
	BILL_CODE int AUTO_INCREMENT,
	USER_ID varchar(10),
	BILL_ISSU_DE date not null,	/* 계산서 발급날짜 */
	BILL_TOTAMT int,	/* 총 가격 */
	BHF_CODE int,


	PRIMARY KEY(BILL_CODE),
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE
	FOREIGN KEY(BHF_CODE) REFERENCES branch_office(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 상품대분류카테고리 테이블 */
create table large_classification_category (
	LCLASCTGRY_CODE int AUTO_INCREMENT,
	LCLASCTGRY_NM varchar(30) not null,
	LCLASCTGRY_COLOR varchar(10) not null,

	PRIMARY KEY(LCLASCTGRY_CODE)
);

/* 상품세부카테고리 테이블
	상품대분류카테고리 테이블을 참조함
 */
create table detail_category (
	DETAILCTGRY_CODE int AUTO_INCREMENT,
	LCLASCTGRY_CODE int not null,
	DETAILCTGRY_NM varchar(30) not null,
	DETAILCTGRY_COLOR varchar(10) not null,

	PRIMARY KEY(DETAILCTGRY_CODE),
	FOREIGN KEY(LCLASCTGRY_CODE) REFERENCES large_classification_category(LCLASCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 타일을 배정한다 테이블 ( 세부카테고리위치 )
	상품세부카테고리, 타일 테이블을 참조함
 */
create table detail_category_location (
	DETAILCTGRY_CODE int not null,
	TILE_CODE int not null,
	DETAILCTGRYLC_APPLC_DE date not null,
	DETAILCTGRYLC_END_DE date,

	PRIMARY KEY(DETAILCTGRY_CODE, TILE_CODE),
	FOREIGN KEY(DETAILCTGRY_CODE) REFERENCES detail_category(DETAILCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(TILE_CODE) REFERENCES tile(TILE_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);
	 

/* 상품 테이블
	상품세부카테고리 테이블을 참조함
 */
create table goods (
	GOODS_CODE int AUTO_INCREMENT,
	GOODS_NM varchar(20) not null, /* 상품 이름 */
	GOODS_PC int not null,	/* 상품가격 */
	GOODS_DC varchar(500) not null,	/* 상품설명 */
	GOODS_FLPTH varchar(100),
	
	DETAILCTGRY_CODE int not null,

	PRIMARY KEY(GOODS_CODE),
	FOREIGN KEY(DETAILCTGRY_CODE) REFERENCES detail_category(DETAILCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 계산서를 구성하다 테이블 ( 구매한 상품 )
	계산서, 상품, 쿠폰 테이블을 참조함
 */
create table purchase_goods (
	BILL_CODE int not null,
	GOODS_CODE int not null,
	COUPON_CODE int,
	PURCHSGOODS_QY int not null,	/* 구매수량 */

	PRIMARY KEY(BILL_CODE, GOODS_CODE),
	FOREIGN KEY(BILL_CODE) REFERENCES bill(BILL_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(GOODS_CODE) REFERENCES goods(GOODS_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(COUPON_CODE) REFERENCES coupon(COUPON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);







/* 상품세부카테고리에 쿠폰을 생성하다 */

create table coupon_detailcategory_creation (
	coupon_code integer references coupon_code(coupon),
	DETAILCTGRY_CODE integer references detailctgry_code(detail_category),
	coupon_co integer not null
);

/* 상품에 쿠폰을 생성하다 */

create table coupon_goods_creation (
	coupon_code integer references coupon_code(coupon),
	goods_code integer references goods_code(goods),
	coupon_co integer not null
);



/* 게시판 카테고리 */

create table bbs_category (
	BBSCTGRY_CODE integer auto_increment primary key,
	bbsctgry_nm varchar(50) not null
);


/* 게시글 */

create table bbsctt (
	bbsctt_code integer auto_increment primary key,
	bbsctt_sj varchar(100) not null,
	bbsctt_cn varchar(1000) not null
);

/* 이벤트 */
create table event (
	bbsctt_code integer not null references bbsctt_code(bbsctt),
	event_begin_de timestamp not null,
	event_end_de timestamp not null
);

/* 게시글을 작성할 수 있다*/
create table bbsctt_writing (
	`bhf_code` int(11) not null,
	`user_id` VARCHAR(10) NOT NULL,
	`bbsctgry_code` INT(11) NOT NULL,
	`bbsctt_code` INT(11) NOT NULL,	`bbsctt_rgsde` DATE NOT NULL,
	`bbsctt_rdcnt` INT(11) NULL DEFAULT '0',
	CONSTRAINT `bbsctt_writing_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT `bbsctt_writing_ibfk_3` FOREIGN KEY (`BHF_CODE`) REFERENCES `branch_office` (`BHF_CODE`) ON UPDATE CASCADE ON DELETE CASCADE
);


/* 댓글 */

create table reply (
	reply_code integer auto_increment primary key,
	reply_cn varchar(500) not null
);

/* 댓글을 작성할 수 있다 */
create table reply_writing (
	user_id varchar(10) not null references user_id(user),
	bbsctt_code integer not null references bbsctt_code(bbsctt),
	reply_code integer not null references reply_code(reply),
	reply_rgsde date not null
);



/* 결제 수단 */

create table settlement_method (
	setle_mth_code integer auto_increment primary key,
	setle_mth_nm varchar(50) not null
);

/* 계산서를 결제하다 */

create table settlement_infomation (
	bill_code integer not null references bill_code(bill),
	setle_mth_code integer not null references setle_mth_code(settlement_method),
	stprc integer not null
);


create table supply_enterprise (
   `USER_ID` VARCHAR(10) NOT NULL,
   `SUPLY_ENTRPS_NM` VARCHAR(50) NOT NULL,
   `SUPLY_ENTRPS_TELNO` VARCHAR(13) NULL DEFAULT NULL,
   `CNTRCT_BEGIN_DE` DATE NOT NULL,
   `CNTRCT_END_DE` DATE NOT NULL,
   PRIMARY KEY (`USER_ID`),
   INDEX `SUPLY_ENTRPS_NM` (`SUPLY_ENTRPS_NM`),
   CONSTRAINT `supply_enterprise_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON UPDATE CASCADE ON DELETE CASCADE
);


create table supply_goods (
   `USER_ID` VARCHAR(10) NOT NULL,
   `GOODS_CODE` INT(11) NOT NULL,
   `BHF_CODE` INT(11) NOT NULL,
   `WRHOUSNG_QY` INT(11) NOT NULL,
   `WRHOUSNG_DE` DATE NOT NULL,
   `PUCHAS_PC` INT(11) NOT NULL,
   `PUCHAS_DE` DATE NOT NULL,
   `DISTB_DE` DATE NOT NULL,
   `INVNTRY_QY` INT(11) NOT NULL,
   PRIMARY KEY (`USER_ID`, `GOODS_CODE`),
   INDEX `GOODS_CODE` (`GOODS_CODE`),
   INDEX `bhf_code` (`BHF_CODE`),
   CONSTRAINT `supply_goods_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON UPDATE CASCADE ON DELETE CASCADE,
   CONSTRAINT `supply_goods_ibfk_2` FOREIGN KEY (`GOODS_CODE`) REFERENCES `goods` (`GOODS_CODE`) ON UPDATE CASCADE ON DELETE CASCADE,
   CONSTRAINT `supply_goods_ibfk_3` FOREIGN KEY (`BHF_CODE`) REFERENCES `branch_office` (`BHF_CODE`) ON UPDATE CASCADE ON DELETE CASCADE
)

CREATE TABLE help_board (
	`BNO` INT(11) AUTO_INCREMENT,
	`TITLE` VARCHAR(30) NOT NULL,
	`CONTENT` VARCHAR(300) NOT NULL,
	`WRITER` VARCHAR(30) NOT NULL,
	`REGIDATE` DATE,
	PRIMARY KEY (`BNO`)
);

CREATE TABLE help_board (
	bno int(11) NOT NULL AUTO_INCREMENT,
	title varchar(30) NOT NULL,
	content varchar(300) NOT NULL,
	writer varchar(30) NOT NULL,
	regidate date DEFAULT NULL,
	PRIMARY KEY (bno)
)

create table help_reply(
rno int NOT NULL AUTO_INCREMENT,
	bno int not null,
	replytext varchar(1000) not null,
	replyer varchar(50) not null,
	regidate TIMESTAMP NOT NULL DEFAULT now(),
	updatedate TIMESTAMP NOT NULL DEFAULT now(),

	primary key(rno),
	FOREIGN KEY(bno) REFERENCES help_board(bno) ON UPDATE CASCADE ON DELETE CASCADE
);

create table point(
	USER_ID varchar(10) not null,
	POINT_AMOUNT integer default 0 not null,
	constraint POINT_pk primary key(USER_ID),
	constraint POINT_USER_ID_fk foreign key(USER_ID) references user(USER_ID)
);