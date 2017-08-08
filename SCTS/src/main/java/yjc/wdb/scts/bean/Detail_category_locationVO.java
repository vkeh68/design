package yjc.wdb.scts.bean;

import java.sql.Date;

public class Detail_category_locationVO {
	private int		detailctgry_code;
	private int		tile_code;
	private Date	detailctgrylc_applc_de;
	private Date	detailctgrylc_end_de;
	
	public int getDetailctgry_code() {
		return detailctgry_code;
	}
	public void setDetailctgry_code(int detailctgry_code) {
		this.detailctgry_code = detailctgry_code;
	}
	public int getTile_code() {
		return tile_code;
	}
	public void setTile_code(int tile_code) {
		this.tile_code = tile_code;
	}
	public Date getDetailctgrylc_applc_de() {
		return detailctgrylc_applc_de;
	}
	public void setDetailctgrylc_applc_de(Date detailctgrylc_applc_de) {
		this.detailctgrylc_applc_de = detailctgrylc_applc_de;
	}
	public Date getDetailctgrylc_end_de() {
		return detailctgrylc_end_de;
	}
	public void setDetailctgrylc_end_de(Date detailctgrylc_end_de) {
		this.detailctgrylc_end_de = detailctgrylc_end_de;
	}
	
}
