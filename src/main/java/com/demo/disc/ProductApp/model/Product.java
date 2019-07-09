package com.demo.disc.ProductApp.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
public class Product {

	private int id;
	private String name;
	private String description;
	private BigDecimal price;
	private String pic;
	private int quantity = 0;

	public int getProductId() {
		return id;
	}

	public void setProductId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return name;
	}

	public void setProductName(String name) {
		this.name = name;
	}

	public String getProductDesc() {
		return description;
	}

	public void setProductDesc(String description) {
		this.description = description;
	}

	public BigDecimal getPrice(){
		return price;
	}

	public void setPrice(BigDecimal price){
		this.price = price;
	}

	public String getPic(){
		return pic;
	}

	public void setPic(String pic){
		this.pic = pic;
	}

	public int getQuantity(){return quantity;}

	public void setQuantity(int quantity){this.quantity = quantity;}

}
