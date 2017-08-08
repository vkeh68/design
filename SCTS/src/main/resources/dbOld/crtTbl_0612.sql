/* 기존에 있던 층 정보 테이블에서
 * 해당 층에 몇개의 타일이 존재하는지에 대한 정보를 표시하기 위해
 * X값과 Y값을 추가하였음
 */


create table FLOOR_INFORMATION (
	BHF_CODE int not null,
	DRW_CODE int not null,
	FLOORINFO_FLOOR varchar(10) not null,	/* 층 (몇층인지) */
	FLOORINFO_RGSDE date,	/* 등록 날짜 (도면이 해당 층에 등록된 날짜) */
	SIZE_X int not null,
	SIZE_Y int not null,
	PRIMARY KEY(BHF_CODE, DRW_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES BRANCH_OFFICE(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DRW_CODE) REFERENCES DRAWING(DRW_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);