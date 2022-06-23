package models;

public class Brand {

	private int brand_id;
	private String brandName;
	
	public Brand() {
		
	}

	public Brand(int brand_id, String brandName) {
		super();
		this.brand_id = brand_id;
		this.brandName = brandName;
	}

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	
}
