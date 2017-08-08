package yjc.wdb.scts.bean;

public class TileVO {
	private int		tile_code;
	private String	tile_nm;
	private int		tile_crdnt_x;
	private int		tile_crdnt_y;
	private int		beacon_code;
	private int		drw_code;
	
	public int getTile_crdnt_x() {
		return tile_crdnt_x;
	}
	public void setTile_crdnt_x(int tile_crdnt_x) {
		this.tile_crdnt_x = tile_crdnt_x;
	}
	public int getTile_crdnt_y() {
		return tile_crdnt_y;
	}
	public void setTile_crdnt_y(int tile_crdnt_y) {
		this.tile_crdnt_y = tile_crdnt_y;
	}
	public int getDrw_code() {
		return drw_code;
	}
	public void setDrw_code(int drw_code) {
		this.drw_code = drw_code;
	}
	public int getTile_code() {
		return tile_code;
	}
	public void setTile_code(int tile_code) {
		this.tile_code = tile_code;
	}
	public String getTile_nm() {
		return tile_nm;
	}
	public void setTile_nm(String tile_nm) {
		this.tile_nm = tile_nm;
	}
	public int getBeacon_code() {
		return beacon_code;
	}
	public void setBeacon_code(int beacon_code) {
		this.beacon_code = beacon_code;
	}
}
