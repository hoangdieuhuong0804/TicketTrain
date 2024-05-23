package Model;

public class StatusTickets {
	private String idstatus;
	private String mash;
	private String idtype;
	private int totaltickets;
	private int booked;
	public StatusTickets(String idstatus, String mash,String idtype, int totaltickets, int booked) {
		super();
		this.idstatus = idstatus;
		this.mash = mash;
		this.idtype = idtype;
		this.totaltickets = totaltickets;
		this.booked = booked;
	}
	
	public StatusTickets() {
		super();
		this.idstatus = idstatus;
		this.mash = mash;
		this.idtype = idtype;
		this.totaltickets = totaltickets;
		this.booked = booked;		
	}

	public String getIdstatus() {
		return idstatus;
	}

	public void setIdstatus(String idstatus) {
		this.idstatus = idstatus;
	}

	public String getMash() {
		return mash;
	}

	public void setMash(String mash) {
		this.mash = mash;
	}

	public int getTotaltickets() {
		return totaltickets;
	}

	public void setTotaltickets(int totaltickets) {
		this.totaltickets = totaltickets;
	}

	public int getBooked() {
		return booked;
	}

	public void setBooked(int booked) {
		this.booked = booked;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	@Override
	public String toString() {
		return "StatusTickets [idstatus=" + idstatus + ", mash=" + mash + ", idtype=" + idtype + ", totaltickets="
				+ totaltickets + ", booked=" + booked + "]";
	}

	
	
	
	
	

}
