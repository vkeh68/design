package yjc.wdb.scts.bean;

public class GoodsVO {
	private int		goods_code;
	private String 	goods_nm;
	private int		goods_pc;
	private String	goods_dc;
	private int		detailctgry_code;
	private String 	goods_flpth;
	private String goods_image;
	
	public String getGoods_flpth() {
		return goods_flpth;
	}
	public String getGoods_image() {
		return goods_image;
	}
	public void setGoods_image(String goods_image) {
		this.goods_image = goods_image;
	}
	public void setGoods_flpth(String goods_flpth) {
		this.goods_flpth = goods_flpth;
	}
	public String getGoods_nm() {
		return goods_nm;
	}
	public void setGoods_nm(String goods_nm) {
		this.goods_nm = goods_nm;
	}
	
	public int getGoods_code() {
		return goods_code;
	}
	public void setGoods_code(int goods_code) {
		this.goods_code = goods_code;
	}
	public int getGoods_pc() {
		return goods_pc;
	}
	public void setGoods_pc(int goods_pc) {
		this.goods_pc = goods_pc;
	}
	public String getGoods_dc() {
		return goods_dc;
	}
	public void setGoods_dc(String goods_dc) {
		this.goods_dc = goods_dc;
	}
	public int getDetailctgry_code() {
		return detailctgry_code;
	}
	public void setDetailctgry_code(int detailctgry_code) {
		this.detailctgry_code = detailctgry_code;
	}
	
}
