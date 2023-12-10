package admin;



public class productData {
	
	
	private Integer productID;
	private String description;
	private String category;
	private Integer qty;
	private Integer pricemed;
	private Integer pricelrg;
	private String availability;
	private String image;
	
		public productData(Integer productID, String description, String category, Integer qty, Integer pricemed,
			Integer pricelrg, String availability, String image) {
		
		this.productID = productID;
		this.description = description;
		this.category = category;
		this.qty = qty;
		this.pricemed = pricemed;
		this.pricelrg = pricelrg;
		this.availability = availability;
		this.image = image;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getPricemed() {
		return pricemed;
	}
	public void setPricemed(Integer pricemed) {
		this.pricemed = pricemed;
	}
	public Integer getPricelrg() {
		return pricelrg;
	}
	public void setPricelrg(Integer pricelrg) {
		this.pricelrg = pricelrg;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public Integer getProductID() {
		return productID;
	}
	public void setProductID(Integer productID) {
		this.productID = productID;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
