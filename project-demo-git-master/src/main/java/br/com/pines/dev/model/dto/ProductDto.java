package br.com.pines.dev.model.dto;

import br.com.pines.dev.model.Product;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
public class ProductDto {

    private String name;
    
    private String uriImg;

    private String description;

    private BigDecimal price;

    public ProductDto(String uriImg, String name, String description, BigDecimal price) {
    	this.uriImg = uriImg;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductDto(ProductDto productDto){

    }
    
    public String getUriImg() {
  		return uriImg;
  	}
    
  	public void setUriImg(String uriImg) {
  		this.uriImg = uriImg;
  	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Product conversor() {
        return new Product(this.uriImg, this.name, this.description, this.price);
    }
}
