package yjc.wdb.scts.bean;

import java.sql.Timestamp;

public class CourseVO {
	private String		user_id;
	private int			tile_code;
	private int			cours_stay_time;
	private Timestamp	cours_pasng_time;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getTile_code() {
		return tile_code;
	}
	public void setTile_code(int tile_code) {
		this.tile_code = tile_code;
	}
	public int getCours_stay_time() {
		return cours_stay_time;
	}
	public void setCours_stay_time(int cours_stay_time) {
		this.cours_stay_time = cours_stay_time;
	}
	public Timestamp getCours_pasng_time() {
		return cours_pasng_time;
	}
	public void setCours_pasng_time(Timestamp cours_pasng_time) {
		this.cours_pasng_time = cours_pasng_time;
	}

}
