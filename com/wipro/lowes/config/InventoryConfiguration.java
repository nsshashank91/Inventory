package com.wipro.lowes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.wipro.lowes.dto.ProductStock;
import com.wipro.lowes.entity.Inventory;


@Configuration
public class InventoryConfiguration {
	
	@Bean("inventory-proto")
	@Scope("prototype")
	public Inventory getInventoryInstance() {
		return new Inventory();
	}
	

	
	
	@Bean("productStock-proto")
	@Scope("prototype")
	public ProductStock getProductStockInstance() {
		return new ProductStock();
	}
	

}
