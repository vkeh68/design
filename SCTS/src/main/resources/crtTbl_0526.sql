/* ���� ���̺� */
create table BRANCH_OFFICE (
	BHF_CODE int AUTO_INCREMENT,	/* ���� �ڵ� */
	BHF_NM varchar(50) not null,	/* ���� �̸� */
	BHF_ADRES varchar(100) not null,	/* ���� �ּ� */
	BHF_TELNO varchar(20) not null,	/* ���� ��ȭ��ȣ */
	PRIMARY KEY(BHF_CODE)
);

/* ���� ���̺� */
create table DRAWING (
	DRW_CODE int AUTO_INCREMENT,
	DRW_FLPTH varchar(100) not null,	/* ���� ���� ��� */
	PRIMARY KEY(DRW_CODE)
);

/* �� ���� ���̺�
	����, ���� ���̺��� ������
 */
create table FLOOR_INFORMATION (
	BHF_CODE int not null,
	DRW_CODE int not null,
	FLOORINFO_FLOOR varchar(10) not null,	/* �� (��������) */
	FLOORINFO_RGSDE date,	/* ��� ��¥ (������ �ش� ���� ��ϵ� ��¥) */
	PRIMARY KEY(BHF_CODE, DRW_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES BRANCH_OFFICE(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DRW_CODE) REFERENCES DRAWING(DRW_CODE) ON UPDATE CASCADE ON DELETE CASCADE

);


/* ���� ���̺�
	���� ���̺��� ������
 */
create table BEACON (
	BEACON_CODE int AUTO_INCREMENT,
	BEACON_MJR int not null,	/* ���� ������ */
	BEACON_MNR int not null,	/* ���� ���̳� */
	BEACON_STTUS varchar(10) not null, /* ���� ��� */
	BHF_CODE int not null,	/* ���� �ڵ� */
	PRIMARY KEY(BEACON_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES BRANCH_OFFICE(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* Ÿ�� ���̺� */
create table TILE (
	TILE_CODE int AUTO_INCREMENT,
	TILE_NM varchar(10) not null,
	BEACON_CODE int,
	PRIMARY KEY(TILE_CODE),
	FOREIGN KEY(BEACON_CODE) REFERENCES BEACON(BEACON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* ȸ�� ���̺� */
create table USER (
	USER_ID varchar(10) not null,
	USER_PASSWORD varchar(10) not null,
	USER_ADRES varchar(100) not null,
	USER_NM varchar(10) not null,
	USER_BRTHDY int not null,		/* ȸ�� ������� */
	USER_MBTLNUM varchar(15) not null,	/* ȸ�� �޴��� ��ȣ */
	USER_EMAIL varchar(30) not null,
	USER_SEXDSTN varchar(10) not null,	/* ȸ�� ���� */
	USER_MRRG_AT varchar(3) not null,	/* ȸ�� ȥ�� ���� */
	PRIMARY KEY(USER_ID)
);

/* ��� ���̺�
	Ÿ��, ����� ���̺��� ������
 */
create table COURSE (
	USER_ID varchar(10),
	TILE_CODE int not null,
	COURS_STAY_TIME int default 0, /* �ӹ� �ð� */
	COURS_PASNG_TIME timestamp default now(), /* ������ �ð�*/
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(TILE_CODE) REFERENCES TILE(TILE_CODE) ON UPDATE CASCADE ON DELETE CASCADE

);

/* �μ� ���̺� */
create table DEPARTMENT (
	DEPT_CODE int AUTO_INCREMENT,
	DEPT_NM varchar(10) not null,
	PRIMARY KEY(DEPT_CODE)
);

/* �μ��� ���Ѵ� ( �μ� �Ҽ� ) ���̺�
	����, �μ� ���̺��� ������
 */
create table DEPARTMENT_POSITION (
	DEPT_CODE int not null,
	BHF_CODE int not null,
	DEPTPSITN_ABL_DE date,
	DEPTPSITN_ESTBL_DE date not null, /* �μ� ���� ��¥*/
	FOREIGN KEY(DEPT_CODE) REFERENCES DEPARTMENT(DEPT_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(BHF_CODE) REFERENCES BRANCH_OFFICE(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* ���� ���̺�
	ȸ�� ���̺��� ������
 */
create table EMPLOYEE (
	USER_ID varchar(10) not null,
	EMP_IHIDNUM varchar(15) not null, /* ���� �ֹε�Ϲ�ȣ */
	EMP_ANSLRY int,	/* ���� ���� */
	EMP_ACNUTNO varchar(20),	/* ���� ���¹�ȣ */
	EMP_ENCPN date not null,	/* ���� �Ի��� */
	EMP_RETIRE date,	/* ���� ��糯¥ */
	PRIMARY KEY(USER_ID),
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE
	
);

/* ������ �Ҽӵȴ� ���̺� ( ���� �Ҽ� )
	ȸ��, �μ� ���̺��� ������
 */
create table EMPLOYEE_POSITION (
	USER_ID varchar(10) not null,
	DEPT_CODE int not null,
	EMPPSITN_RSPOFC varchar(10) not null,	/* ��å */
	EMPPSITN_GNFD_DE date not null,	/* �߷����� */
	EMPPSITN_LEAV_DE date,	/* �μ��������� */
	PRIMARY KEY(USER_ID, DEPT_CODE),
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DEPT_CODE) REFERENCES DEPARTMENT(DEPT_CODE) ON UPDATE CASCADE ON DELETE CASCADE
	
);

/* ���� ���̺� */
create table COUPON (
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
create table COUPON_HOLD (
	USER_ID varchar(10) not null,
	COUPON_CODE int not null,
	COUPONHOLD_USE_AT varchar(10) not null, /* ���� ���� ( ��뿩�� ) */
	PRIMARY KEY(USER_ID, COUPON_CODE),
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(COUPON_CODE) REFERENCES COUPON(COUPON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* ��꼭 ���̺�
	ȸ�� ���̺��� ������
 */
create table BILL (
	BILL_CODE int AUTO_INCREMENT,
	USER_ID varchar(10),
	BILL_ISSU_DE date not null,	/* ��꼭 �߱޳�¥ */
	BILL_TOTAMT int not null,	/* �� ���� */
	PRIMARY KEY(BILL_CODE),
	FOREIGN KEY(USER_ID) REFERENCES USER(USER_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

/* ��ǰ��з�ī�װ� ���̺� */
create table LARGE_CLASSIFICATION_CATEGORY (
	LCLASCTGRY_CODE int AUTO_INCREMENT,
	LCLASCTGRY_NM varchar(10) not null,
	LCLASCTGRY_COLOR varchar(20) not null,
	PRIMARY KEY(LCLASCTGRY_CODE)
);

/* ��ǰ����ī�װ� ���̺�
	��ǰ��з�ī�װ� ���̺��� ������
 */
create table DETAIL_CATEGORY (
	DETAILCTGRY_CODE int AUTO_INCREMENT,
	LCLASCTGRY_CODE int not null,
	DETAILCTGRY_NM varchar(10) not null,
	DETAILCTGRY_COLOR varchar(20) not null,
	PRIMARY KEY(DETAILCTGRY_CODE),
	FOREIGN KEY(LCLASCTGRY_CODE) REFERENCES LARGE_CLASSIFICATION_CATEGORY(LCLASCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* Ÿ���� �����Ѵ� ���̺� ( ����ī�װ���ġ )
	��ǰ����ī�װ�, Ÿ�� ���̺��� ������
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
	 

/* ��ǰ ���̺�
	��ǰ����ī�װ� ���̺��� ������
 */
create table GOODS (
	GOODS_CODE int AUTO_INCREMENT,
	GOODS_PC int not null,	/* ��ǰ���� */
	GOODS_DC varchar(500) not null,	/* ��ǰ���� */
	DETAILCTGRY_CODE int not null,
	PRIMARY KEY(GOODS_CODE),
	FOREIGN KEY(DETAILCTGRY_CODE) REFERENCES DETAIL_CATEGORY(DETAILCTGRY_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* ��꼭�� �����ϴ� ���̺� ( ������ ��ǰ )
	��꼭, ��ǰ, ���� ���̺��� ������
 */
create table PURCHASE_GOODS (
	BILL_CODE int not null,
	GOODS_CODE int not null,
	COUPON_CODE int,
	PURCHSGOODS_QY int not null,	/* ���ż��� */
	PRIMARY KEY(BILL_CODE, GOODS_CODE),
	FOREIGN KEY(BILL_CODE) REFERENCES BILL(BILL_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(GOODS_CODE) REFERENCES GOODS(GOODS_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(COUPON_CODE) REFERENCES COUPON(COUPON_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);

/* Ÿ���� ���Ѵ� ( ���� ) ���̺�
	����, Ÿ�� ���̺��� ������
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

