package Model;

public class TypeTickets {
	private String idtypes;
	private String type;
	private int price;
	public TypeTickets(String idtypes, String type, int price) {
		super();
		this.idtypes = idtypes;
		this.type = type;
		this.price = price;
	}
	
	public TypeTickets() {
		super();
		this.idtypes = idtypes;
		this.type = type;
		this.price = price;
	}
	public String getIdtypes() {
		return idtypes;
	}
	public void setIdtypes(String idtypes) {
		this.idtypes = idtypes;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	

}
