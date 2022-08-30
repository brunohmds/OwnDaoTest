package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String brand;
	private String name;
	private Double price;
	private Integer quantity;
	private Double totalPrice;
	
	private Department department;

	public Product(Integer id, String brand, String name, Double price, Integer quantity, Department department) {
		this.id = id;
		this.brand = brand;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.department = department;
	}
	
	public Product() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Double getTotalPrice() { 
		totalPrice = quantity*price;
		return totalPrice;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", brand=" + brand + ", name=" + name + ", price=" + price + ", quantity="
				+ quantity + ", totalPrice=" + getTotalPrice() + ", department=" + department + "]";
	}
}
