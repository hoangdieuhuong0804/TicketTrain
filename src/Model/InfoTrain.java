package Model;

public class InfoTrain {
	private String mash;
	private String nameTrain;
	private String locationStartTrain;
	private String locationEndTrain;
	private String timeStartTrain;
	private String expectedendTime;
	private String direction;
	
	public InfoTrain(String mash, String nameTrain, String locationStartTrain, String locationEndTrain,
			String timeStartTrain, String expectedendTime, String direction) {
		super();
		this.mash = mash;
		this.nameTrain = nameTrain;
		this.locationStartTrain = locationStartTrain;
		this.locationEndTrain = locationEndTrain;
		this.timeStartTrain = timeStartTrain;
		this.expectedendTime = expectedendTime;
		this.direction = direction;
	}
	
	public InfoTrain() {
		super();
		this.mash = mash;
		this.nameTrain = nameTrain;
		this.locationStartTrain = locationStartTrain;
		this.locationEndTrain = locationEndTrain;
		this.timeStartTrain = timeStartTrain;
		this.expectedendTime = expectedendTime;
		this.direction = direction;
	}

	public String getMash() {
		return mash;
	}

	public void setMash(String mash) {
		this.mash = mash;
	}

	public String getNameTrain() {
		return nameTrain;
	}

	public void setNameTrain(String nameTrain) {
		this.nameTrain = nameTrain;
	}

	public String getLocationStartTrain() {
		return locationStartTrain;
	}

	public void setLocationStartTrain(String locationStartTrain) {
		this.locationStartTrain = locationStartTrain;
	}

	public String getLocationEndTrain() {
		return locationEndTrain;
	}

	public void setLocationEndTrain(String locationEndTrain) {
		this.locationEndTrain = locationEndTrain;
	}

	public String getTimeStartTrain() {
		return timeStartTrain;
	}

	public void setTimeStartTrain(String timeStartTrain) {
		this.timeStartTrain = timeStartTrain;
	}

	public String getExpectedendTime() {
		return expectedendTime;
	}

	public void setExpectedendTime(String expectedendTime) {
		this.expectedendTime = expectedendTime;
	}
	
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "InfoTrain [mash=" + mash + ", nameTrain=" + nameTrain + ", locationStartTrain=" + locationStartTrain
				+ ", locationEndTrain=" + locationEndTrain + ", timeStartTrain=" + timeStartTrain + ", expectedendTime="
				+ expectedendTime + ", direction=" + direction + "]";
	}
	
	
	
	
	

}
