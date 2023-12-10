package admin;

import java.sql.Date;
import java.sql.Time;

public class transaction {
		
	private Integer salesID;
	private Integer empID;
	private String action;
	private Integer orderID;
	private Integer total;
	private Time time;
	private Date date;
	
	public transaction(Integer salesID, Integer empID, String action, Integer orderID, Integer total, Time time,
			Date date) {
		
		this.salesID = salesID;
		this.empID = empID;
		this.action = action;
		this.orderID = orderID;
		this.total = total;
		this.time = time;
		this.date = date;
	}
	
	public Integer getSalesID() {
		return salesID;
	}

	public void setSalesID(Integer salesID) {
		this.salesID = salesID;
	}

	public Integer getEmpID() {
		return empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Integer getOrderID() {
		return orderID;
	}
	
	public void setOrderID(Integer source) {
		this.orderID = source;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
