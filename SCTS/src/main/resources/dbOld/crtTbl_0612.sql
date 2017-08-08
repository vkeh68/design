/* ������ �ִ� �� ���� ���̺���
 * �ش� ���� ��� Ÿ���� �����ϴ����� ���� ������ ǥ���ϱ� ����
 * X���� Y���� �߰��Ͽ���
 */


create table FLOOR_INFORMATION (
	BHF_CODE int not null,
	DRW_CODE int not null,
	FLOORINFO_FLOOR varchar(10) not null,	/* �� (��������) */
	FLOORINFO_RGSDE date,	/* ��� ��¥ (������ �ش� ���� ��ϵ� ��¥) */
	SIZE_X int not null,
	SIZE_Y int not null,
	PRIMARY KEY(BHF_CODE, DRW_CODE),
	FOREIGN KEY(BHF_CODE) REFERENCES BRANCH_OFFICE(BHF_CODE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(DRW_CODE) REFERENCES DRAWING(DRW_CODE) ON UPDATE CASCADE ON DELETE CASCADE
);