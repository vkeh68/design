/* ���� ���̺� */
create table branch_office (
	BHF_CODE int AUTO_INCREMENT,	/* ���� �ڵ� */
	BHF_NM varchar(50) not null,	/* ���� �̸� */
	BHF_ADRES varchar(100) not null,	/* ���� �ּ� */
	BHF_TELNO varchar(20) not null,	/* ���� ��ȭ��ȣ */
	PRIMARY KEY(BHF_CODE)
);

/* ���� ���̺� */
create table drawing (
	DRW_CODE int AUTO_INCREMENT,
	DRW_FLPTH varchar(100) not null,	/* ���� ���� ��� */
	PRIMARY KEY(DRW_CODE)
);

/* �� ���� ���̺�
	����, ���� ���̺��� ������
 */

create table floor_information (
	BHF_CODE int not null,
	DRW_CODE int not null,
	FLOORINFO_FLOOR varchar(10) not null,	/* �� (��������) */
	FLOORINFO_RGSDE date,	/* ��� ��¥ (������ �ش� ���� ��ϵ� ��¥) */
	SIZE_X int not null,
	SIZE_Y int not null,
	PRIMARY KEY(BHF_CODE, DRW_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES branch_office(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DRW_CODE) REFERENCES drawing(DRW_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);


/* ���� ���̺�
	���� ���̺��� ������
 */
create table beacon (
	BEACON_CODE int AUTO_INCREMENT,
	BEACON_MJR int not null,	/* ���� ������ */
	BEACON_MNR int not null,	/* ���� ���̳� */
	BEACON_STTUS varchar(10) not null, /* ���� ��� */
	BHF_CODE int not null,	/* ���� �ڵ� */
	PRIMARY KEY(BEACON_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES branch_office(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* Ÿ�� ���̺� */
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

/* ȸ�� ���̺� */
create table user (
	USER_ID varchar(10) not null,
	USER_PASSWORD varchar(10) not null,
	USER_ADRES varchar(100) not null,
	USER_NM varchar(10) not null,
	USER_BRTHDY varchar(20) not null,		/* ȸ�� ������� */
	USER_MBTLNUM varchar(15) not null,	/* ȸ�� �޴��� ��ȣ */
	USER_EMAIL varchar(30) not null,
	USER_SEXDSTN varchar(10) not null,	/* ȸ�� ���� */
	USER_MRRG_AT varchar(3) not null,	/* ȸ�� ȥ�� ���� */
	PRIMARY KEY(USER_ID)
);

/* ��� ���̺�
	Ÿ��, ����� ���̺��� ������
 */
create table course (
	USER_ID varchar(10),
	TILE_CODE int not null,
	COURS_STAY_TIME int, /* �ӹ� �ð� */
	COURS_PASNG_TIME timestamp default now(), /* ������ �ð�*/
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(TILE_CODE) REFERENCES tile(TILE_CODE) ON UPDATE CASCADE ON DELETE CASCADE

);

/* �μ� ���̺� */
create table department (
	DEPT_CODE int AUTO_INCREMENT,
	DEPT_NM varchar(10) not null,
	PRIMARY KEY(DEPT_CODE)
);

/* �μ��� ���Ѵ� ( �μ� �Ҽ� ) ���̺�
	����, �μ� ���̺��� ������
 */
create table department_position (
	DEPT_CODE int not null,
	BHF_CODE int not null,
	DEPTPSITN_ABL_DE date,
	DEPTPSITN_ESTBL_DE date not null, /* �μ� ���� ��¥*/
	FOREIGN KEY(DEPT_CODE) REFERENCES department(DEPT_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(BHF_CODE) REFERENCES branch_office(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* ���� ���̺�
	ȸ�� ���̺��� ������
 */
create table employee (
	USER_ID varchar(10) not null,
	EMP_IHIDNUM varchar(15) not null, /* ���� �ֹε�Ϲ�ȣ */
	EMP_ANSLRY int,	/* ���� ���� */
	EMP_ACNUTNO varchar(20),	/* ���� ���¹�ȣ */
	EMP_ENCPN date not null,	/* ���� �Ի��� */
	EMP_RETIRE date,	/* ���� ��糯¥ */
	PRIMARY KEY(USER_ID),
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE
	
);

/* ������ �Ҽӵȴ� ���̺� ( ���� �Ҽ� )
	ȸ��, �μ� ���̺��� ������
 */
create table employee_position (
	USER_ID varchar(10) not null,
	DEPT_CODE int not null,
	EMPPSITN_RSPOFC varchar(10) not null,	/* ��å */
	EMPPSITN_GNFD_DE date not null,	/* �߷����� */
	EMPPSITN_LEAV_DE date,	/* �μ��������� */
	PRIMARY KEY(USER_ID, DEPT_CODE),
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DEPT_CODE) REFERENCES department(DEPT_CODE) ON UPDATE CASCADE ON DELETE CASCADE
	
);

/* ���� ���̺� */
create table coupon (
	COUPON_CODE int AUTO_INCREMENT,
	COUPON_NM varchar(20) not null,
	COUPON_CNTNTS varchar(500) not null, /* ���� ���� */
	COUPON_DSCNT varchar(10) not null,	/* ���� ������ */
	COUPON_BEGIN_DE date not null,	/* ���� ���۳�¥ */
	COUPON_END_DE date,	/* ���� ���ᳯ¥ */
	PRIMARY KEY(COUPON_CODE)
);

/* ������ �����Ҽ� �ִ� ( ���� ���� ) ���̺�
	ȸ��, ���� ���̺��� ������
 */
create table coupon_hold (
	USER_ID varchar(10) not null,
	COUPON_CODE int not null,
	COUPONHOLD_USE_AT varchar(10) not null, /* ���� ���� ( ��뿩�� ) */
	PRIMARY KEY(USER_ID, COUPON_CODE),
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(COUPON_CODE) REFERENCES coupon(COUPON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* ��꼭 ���̺�
	ȸ�� ���̺��� ������
 */
create table bill (
	BILL_CODE int AUTO_INCREMENT,
	USER_ID varchar(10),
	BILL_ISSU_DE date not null,	/* ��꼭 �߱޳�¥ */
	BILL_TOTAMT int,	/* �� ���� */
	BHF_CODE int,


	PRIMARY KEY(BILL_CODE),
	FOREIGN KEY(USER_ID) REFERENCES user(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE
	FOREIGN KEY(BHF_CODE) REFERENCES branch_office(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* ��ǰ��з�ī�װ� ���̺� */
create table large_classification_category (
	LCLASCTGRY_CODE int AUTO_INCREMENT,
	LCLASCTGRY_NM varchar(30) not null,
	LCLASCTGRY_COLOR varchar(10) not null,

	PRIMARY KEY(LCLASCTGRY_CODE)
);

/* ��ǰ����ī�װ� ���̺�
	��ǰ��з�ī�װ� ���̺��� ������
 */
create table detail_category (
	DETAILCTGRY_CODE int AUTO_INCREMENT,
	LCLASCTGRY_CODE int not null,
	DETAILCTGRY_NM varchar(30) not null,
	DETAILCTGRY_COLOR varchar(10) not null,

	PRIMARY KEY(DETAILCTGRY_CODE),
	FOREIGN KEY(LCLASCTGRY_CODE) REFERENCES large_classification_category(LCLASCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* Ÿ���� �����Ѵ� ���̺� ( ����ī�װ���ġ )
	��ǰ����ī�װ�, Ÿ�� ���̺��� ������
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
	 

/* ��ǰ ���̺�
	��ǰ����ī�װ� ���̺��� ������
 */
create table goods (
	GOODS_CODE int AUTO_INCREMENT,
	GOODS_NM varchar(20) not null, /* ��ǰ �̸� */
	GOODS_PC int not null,	/* ��ǰ���� */
	GOODS_DC varchar(500) not null,	/* ��ǰ���� */
	GOODS_FLPTH varchar(100),
	
	DETAILCTGRY_CODE int not null,

	PRIMARY KEY(GOODS_CODE),
	FOREIGN KEY(DETAILCTGRY_CODE) REFERENCES detail_category(DETAILCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* ��꼭�� �����ϴ� ���̺� ( ������ ��ǰ )
	��꼭, ��ǰ, ���� ���̺��� ������
 */
create table purchase_goods (
	BILL_CODE int not null,
	GOODS_CODE int not null,
	COUPON_CODE int,
	PURCHSGOODS_QY int not null,	/* ���ż��� */

	PRIMARY KEY(BILL_CODE, GOODS_CODE),
	FOREIGN KEY(BILL_CODE) REFERENCES bill(BILL_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(GOODS_CODE) REFERENCES goods(GOODS_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(COUPON_CODE) REFERENCES coupon(COUPON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);







/* ��ǰ����ī�װ��� ������ �����ϴ� */

create table coupon_detailcategory_creation (
	coupon_code integer references coupon_code(coupon),
	DETAILCTGRY_CODE integer references detailctgry_code(detail_category),
	coupon_co integer not null
);

/* ��ǰ�� ������ �����ϴ� */

create table coupon_goods_creation (
	coupon_code integer references coupon_code(coupon),
	goods_code integer references goods_code(goods),
	coupon_co integer not null
);



/* �Խ��� ī�װ� */

create table bbs_category (
	BBSCTGRY_CODE integer auto_increment primary key,
	bbsctgry_nm varchar(50) not null
);


/* �Խñ� */

create table bbsctt (
	bbsctt_code integer auto_increment primary key,
	bbsctt_sj varchar(100) not null,
	bbsctt_cn varchar(1000) not null
);

/* �̺�Ʈ */
create table event (
	bbsctt_code integer not null references bbsctt_code(bbsctt),
	event_begin_de timestamp not null,
	event_end_de timestamp not null
);

/* �Խñ��� �ۼ��� �� �ִ�*/
create table bbsctt_writing (
	`bhf_code` int(11) not null,
	`user_id` VARCHAR(10) NOT NULL,
	`bbsctgry_code` INT(11) NOT NULL,
	`bbsctt_code` INT(11) NOT NULL,	`bbsctt_rgsde` DATE NOT NULL,
	`bbsctt_rdcnt` INT(11) NULL DEFAULT '0',
	CONSTRAINT `bbsctt_writing_ibfk_1` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`) ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT `bbsctt_writing_ibfk_3` FOREIGN KEY (`BHF_CODE`) REFERENCES `branch_office` (`BHF_CODE`) ON UPDATE CASCADE ON DELETE CASCADE
);


/* ��� */

create table reply (
	reply_code integer auto_increment primary key,
	reply_cn varchar(500) not null
);

/* ����� �ۼ��� �� �ִ� */
create table reply_writing (
	user_id varchar(10) not null references user_id(user),
	bbsctt_code integer not null references bbsctt_code(bbsctt),
	reply_code integer not null references reply_code(reply),
	reply_rgsde date not null
);



/* ���� ���� */

create table settlement_method (
	setle_mth_code integer auto_increment primary key,
	setle_mth_nm varchar(50) not null
);

/* ��꼭�� �����ϴ� */

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