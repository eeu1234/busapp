package DAO;

public class BusStopDTO {
	private String bpk ;
	private String abpk ;
	private String bsname ;
	private String ablat ;
	private String ablng ;
	private String seq ;
	public String getBpk() {
		return bpk;
	}
	public void setBpk(String bpk) {
		this.bpk = bpk;
	}
	public String getAbpk() {
		return abpk;
	}
	public void setAbpk(String abpk) {
		this.abpk = abpk;
	}
	public String getBsname() {
		return bsname;
	}
	public void setBsname(String bsname) {
		this.bsname = bsname;
	}
	public String getAblat() {
		return ablat;
	}
	public void setAblat(String ablat) {
		this.ablat = ablat;
	}
	public String getAblng() {
		return ablng;
	}
	public void setAblng(String ablng) {
		this.ablng = ablng;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getErrscale() {
		return errscale;
	}
	public void setErrscale(String errscale) {
		this.errscale = errscale;
	}
	private String errscale;
}
