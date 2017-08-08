package yjc.wdb.scts.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class EventVO {
	
	private int bbsctt_code;
	private Timestamp event_begin_de;
	private Timestamp event_end_de;
	public int getBbsctt_code() {
		return bbsctt_code;
	}
	public void setBbsctt_code(int bbsctt_code) {
		this.bbsctt_code = bbsctt_code;
	}
	public Timestamp getEvent_begin_de() {
		return event_begin_de;
	}
	public void setEvent_begin_de(Timestamp event_begin_de) {
		this.event_begin_de = event_begin_de;
	}
	public Timestamp getEvent_end_de() {
		return event_end_de;
	}
	public void setEvent_end_de(Timestamp event_end_de) {
		this.event_end_de = event_end_de;
	}

}
