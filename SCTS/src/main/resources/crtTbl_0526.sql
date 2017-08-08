/* 지점 테이블 */
create table BRANCH_OFFICE (
	BHF_CODE int AUTO_INCREMENT,	/* 지점 코드 */
	BHF_NM varchar(50) not null,	/* 지점 이름 */
	BHF_ADRES varchar(100) not null,	/* 지점 주소 */
	BHF_TELNO varchar(20) not null,	/* 지점 전화번호 */
	PRIMARY KEY(BHF_CODE)
);

/* 도면 테이블 */
create table DRAWING (
	DRW_CODE int AUTO_INCREMENT,
	DRW_FLPTH varchar(100) not null,	/* 도면 파일 경로 */
	PRIMARY KEY(DRW_CODE)
);

/* 층 정보 테이블
	지점, 도면 테이블을 참조함
 */
create table FLOOR_INFORMATION (
	BHF_CODE int not null,
	DRW_CODE int not null,
	FLOORINFO_FLOOR varchar(10) not null,	/* 층 (몇층인지) */
	FLOORINFO_RGSDE date,	/* 등록 날짜 (도면이 해당 층에 등록된 날짜) */
	PRIMARY KEY(BHF_CODE, DRW_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES BRANCH_OFFICE(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DRW_CODE) REFERENCES DRAWING(DRW_CODE) ON UPDATE CASCADE ON DELETE CASCADE

);


/* 비콘 테이블
	지점 테이블을 참조함
 */
create table BEACON (
	BEACON_CODE int AUTO_INCREMENT,
	BEACON_MJR int not null,	/* 비콘 메이저 */
	BEACON_MNR int not null,	/* 비콘 마이너 */
	BEACON_STTUS varchar(10) not null, /* 비콘 사애 */
	BHF_CODE int not null,	/* 지점 코드 */
	PRIMARY KEY(BEACON_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES BRANCH_OFFICE(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 타일 테이블 */
create table TILE (
	TILE_CODE int AUTO_INCREMENT,
	TILE_NM varchar(10) not null,
	BEACON_CODE int,
	PRIMARY KEY(TILE_CODE),
	FOREIGN KEY(BEACON_CODE) REFERENCES BEACON(BEACON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 회원 테이블 */
create table USER (
	USER_ID varchar(10) not null,
	USER_PASSWORD varchar(10) not null,
	USER_ADRES varchar(100) not null,
	USER_NM varchar(10) not null,
	USER_BRTHDY int not null,		/* 회원 생년월일 */
	USER_MBTLNUM varchar(15) not null,	/* 회원 휴대폰 번호 */
	USER_EMAIL varchar(30) not null,
	USER_SEXDSTN varchar(10) not null,	/* 회원 성별 */
	USER_MRRG_AT varchar(3) not null,	/* 회원 혼인 여부 */
	PRIMARY KEY(USER_ID)
);

/* 경로 테이블
	타일, 사용자 테이블을 참조함
 */
create table COURSE (
	USER_ID varchar(10),
	TILE_CODE int not null,
	COURS_STAY_TIME int default 0, /* 머문 시간 */
	COURS_PASNG_TIME timestamp default now(), /* 지나간 시간*/
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(TILE_CODE) REFERENCES TILE(TILE_CODE) ON UPDATE CASCADE ON DELETE CASCADE

);

/* 부서 테이블 */
create table DEPARTMENT (
	DEPT_CODE int AUTO_INCREMENT,
	DEPT_NM varchar(10) not null,
	PRIMARY KEY(DEPT_CODE)
);

/* 부서가 속한다 ( 부서 소속 ) 테이블
	지점, 부서 테이블을 참조함
 */
create table DEPARTMENT_POSITION (
	DEPT_CODE int not null,
	BHF_CODE int not null,
	DEPTPSITN_ABL_DE date,
	DEPTPSITN_ESTBL_DE date not null, /* 부서 개설 날짜*/
	FOREIGN KEY(DEPT_CODE) REFERENCES DEPARTMENT(DEPT_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(BHF_CODE) REFERENCES BRANCH_OFFICE(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 직원 테이블
	회원 테이블을 참조함
 */
create table EMPLOYEE (
	USER_ID varchar(10) not null,
	EMP_IHIDNUM varchar(15) not null, /* 직원 주민등록번호 */
	EMP_ANSLRY int,	/* 직원 연봉 */
	EMP_ACNUTNO varchar(20),	/* 직원 계좌번호 */
	EMP_ENCPN date not null,	/* 직원 입사일 */
	EMP_RETIRE date,	/* 직원 퇴사날짜 */
	PRIMARY KEY(USER_ID),
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE
	
);

/* 직원이 소속된다 테이블 ( 직원 소속 )
	회원, 부서 테이블을 참조함
 */
create table EMPLOYEE_POSITION (
	USER_ID varchar(10) not null,
	DEPT_CODE int not null,
	EMPPSITN_RSPOFC varchar(10) not null,	/* 직책 */
	EMPPSITN_GNFD_DE date not null,	/* 발령일자 */
	EMPPSITN_LEAV_DE date,	/* 부서퇴출일자 */
	PRIMARY KEY(USER_ID, DEPT_CODE),
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DEPT_CODE) REFERENCES DEPARTMENT(DEPT_CODE) ON UPDATE CASCADE ON DELETE CASCADE
	
);

/* 쿠폰 테이블 */
create table COUPON (
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
create table COUPON_HOLD (
	USER_ID varchar(10) not null,
	COUPON_CODE int not null,
	COUPONHOLD_USE_AT varchar(10) not null, /* 쿠폰 상태 ( 사용여부 ) */
	PRIMARY KEY(USER_ID, COUPON_CODE),
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(COUPON_CODE) REFERENCES COUPON(COUPON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 계산서 테이블
	회원 테이블을 참조함
 */
create table BILL (
	BILL_CODE int AUTO_INCREMENT,
	USER_ID varchar(10),
	BILL_ISSU_DE date not null,	/* 계산서 발급날짜 */
	BILL_TOTAMT int not null,	/* 총 가격 */
	PRIMARY KEY(BILL_CODE),
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 상품대분류카테고리 테이블 */
create table LARGE_CLASSIFICATION_CATEGORY (
	LCLASCTGRY_CODE int AUTO_INCREMENT,
	LCLASCTGRY_NM varchar(10) not null,
	LCLASCTGRY_COLOR varchar(20) not null,
	PRIMARY KEY(LCLASCTGRY_CODE)
);

/* 상품세부카테고리 테이블
	상품대분류카테고리 테이블을 참조함
 */
create table DETAIL_CATEGORY (
	DETAILCTGRY_CODE int AUTO_INCREMENT,
	LCLASCTGRY_CODE int not null,
	DETAILCTGRY_NM varchar(10) not null,
	DETAILCTGRY_COLOR varchar(20) not null,
	PRIMARY KEY(DETAILCTGRY_CODE),
	FOREIGN KEY(LCLASCTGRY_CODE) REFERENCES LARGE_CLASSIFICATION_CATEGORY(LCLASCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 타일을 배정한다 테이블 ( 세부카테고리위치 )
	상품세부카테고리, 타일 테이블을 참조함
 */
create table DETAIL_CATEGORY_LOCATION (
	DETAILCTGRY_CODE int not null,
	TILE_CODE int not null,
	DETAILCTGRYLC_APPLC_DE date not null,
	DETAILCTGRYLC_END_DE date,
	PRIMARY KEY(DETAILCTGRY_CODE, TILE_CODE),
	FOREIGN KEY(DETAILCTGRY_CODE) REFERENCES DETAIL_CATEGORY(DETAILCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(TILE_CODE) REFERENCES TILE(TILE_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);
	 

/* 상품 테이블
	상품세부카테고리 테이블을 참조함
 */
create table GOODS (
	GOODS_CODE int AUTO_INCREMENT,
	GOODS_PC int not null,	/* 상품가격 */
	GOODS_DC varchar(500) not null,	/* 상품설명 */
	DETAILCTGRY_CODE int not null,
	PRIMARY KEY(GOODS_CODE),
	FOREIGN KEY(DETAILCTGRY_CODE) REFERENCES DETAIL_CATEGORY(DETAILCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 계산서를 구성하다 테이블 ( 구매한 상품 )
	계산서, 상품, 쿠폰 테이블을 참조함
 */
create table PURCHASE_GOODS (
	BILL_CODE int not null,
	GOODS_CODE int not null,
	COUPON_CODE int,
	PURCHSGOODS_QY int not null,	/* 구매수량 */
	PRIMARY KEY(BILL_CODE, GOODS_CODE),
	FOREIGN KEY(BILL_CODE) REFERENCES BILL(BILL_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(GOODS_CODE) REFERENCES GOODS(GOODS_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(COUPON_CODE) REFERENCES COUPON(COUPON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* 타일이 속한다 ( 배정 ) 테이블
	도면, 타일 테이블을 참조함
 */
create table TILE_LOCATION (
	DRW_CODE int not null,
	TILE_CODE int not null,
	TILELC_CRDNT_X int,
	TILELC_CRDNT_Y int,
	TILELC_HG int,
	TILELC_AR int,
	PRIMARY KEY(DRW_CODE, TILE_CODE),
	FOREIGN KEY(DRW_CODE) REFERENCES DRAWING(DRW_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(TILE_CODE) REFERENCES TILE(TILE_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

