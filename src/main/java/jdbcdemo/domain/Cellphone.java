package jdbcdemo.domain;

public class Cellphone implements IHaveId{

	private int id;
	private String brand;
	private String model;
	
	public Cellphone(){};
	
	public Cellphone(String brand, String reg) {
		this.brand = brand;
		this.model = reg;
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
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	@Override
	public String toString() {
		return id + "\t" + brand + "\t" + model;
	}
	
}
