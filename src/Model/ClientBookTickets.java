package Model;

import java.io.Serializable;

public class ClientBookTickets implements Serializable {
	private String username;
	private String idtickets;
	private String mash;
	private String idtype;
	private String locationStartClient;
	private String locationEndClient;
	private String seats;
	private String timeStart;
	private String expectedendTime;
	private String day;
	private int priceTickets;
	private String status;
	
	public ClientBookTickets(String username, String idtickets, String mash, String idtype, String locationStartClient,
			String locationEndClient, String seats, String timeStart, String expectedendTime, String day,
			int priceTickets, String stauts) {
		super();
		this.username = username;
		this.idtickets = idtickets;
		this.mash = mash;
		this.idtype = idtype;
		this.locationStartClient = locationStartClient;
		this.locationEndClient = locationEndClient;
		this.seats = seats;
		this.timeStart = timeStart;
		this.expectedendTime = expectedendTime;
		this.day = day;
		this.priceTickets = priceTickets;
		this.status = stauts;
	}
	
	public ClientBookTickets() {
		super();
		this.username = username;
		this.idtickets = idtickets;
		this.mash = mash;
		this.idtype = idtype;
		this.locationStartClient = locationStartClient;
		this.locationEndClient = locationEndClient;
		this.seats = seats;
		this.timeStart = timeStart;
		this.expectedendTime = expectedendTime;
		this.day = day;
		this.priceTickets = priceTickets;
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIdtickets() {
		return idtickets;
	}

	public void setIdtickets(String idtickets) {
		this.idtickets = idtickets;
	}

	public String getMash() {
		return mash;
	}

	public void setMash(String mash) {
		this.mash = mash;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getLocationStartClient() {
		return locationStartClient;
	}

	public void setLocationStartClient(String locationStartClient) {
		this.locationStartClient = locationStartClient;
	}

	public String getLocationEndClient() {
		return locationEndClient;
	}

	public void setLocationEndClient(String locationEndClient) {
		this.locationEndClient = locationEndClient;
	}

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getExpectedendTime() {
		return expectedendTime;
	}

	public void setExpectedendTime(String expectedendTime) {
		this.expectedendTime = expectedendTime;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}


	public int getPriceTickets() {
		return priceTickets;
	}

	public void setPriceTickets(int priceTickets) {
		this.priceTickets = priceTickets;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	

}
