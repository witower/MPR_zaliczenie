package jdbcdemo.domain;

public class Car implements IHaveId{

	private int id;
	private String brand;
	private String registration;
	
	public Car(){};
	
	public Car(String brand, String reg) {
		this.brand = brand;
		this.registration = reg;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	
	@Override
	public String toString() {
		return id + "\t" + brand + "\t" + registration;
	}
	
}
