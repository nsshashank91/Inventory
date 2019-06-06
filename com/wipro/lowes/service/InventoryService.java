package com.wipro.lowes.service;

import org.springframework.http.ResponseEntity;

public interface InventoryService {

	ResponseEntity<Long> getStockByProductId(long productId);

	ResponseEntity<String> addInventory(long stock, long productId);

	ResponseEntity<String> increaseStock(long stock, long productId);

	ResponseEntity<String> decreaseStock(long stock, long productId);

}