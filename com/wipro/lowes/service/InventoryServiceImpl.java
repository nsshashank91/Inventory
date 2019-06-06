package com.wipro.lowes.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wipro.lowes.config.InventoryConfiguration;
import com.wipro.lowes.dao.InventoryRepository;
import com.wipro.lowes.entity.Inventory;


@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
	
	private Logger logger = Logger.getLogger("InventoryService");

	
	@Autowired
	private InventoryConfiguration configuration;
	
	
	@Autowired
	private InventoryRepository repository;
	
	@Override
	public ResponseEntity<Long> getStockByProductId(long productId){
		logger.info("get stock by product for id "+productId+" service invoked");
		try {
			long stock = repository.findByProductId(productId).getStock();
			return new ResponseEntity<Long>(stock,HttpStatus.ACCEPTED); 
		}
		catch(Exception e) {
			return new ResponseEntity<Long>(-1L,HttpStatus.BAD_REQUEST); 
		}
		
	}
	
	@Override
	public ResponseEntity<String> addInventory(long stock, long productId) {
		logger.info("Add Inventory service invoked");
		try {
			Inventory inv = repository.findByProductId(productId);
			if(inv!=null) {
				throw new Exception("Inventory already exists for product "+productId);
			}
			Inventory inventory = configuration.getInventoryInstance();
			inventory.setProductId(productId);
			inventory.setStock(stock);
			repository.save(inventory);
			return new ResponseEntity<String>("Inventory add successfully added for product "+productId, HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Unable to add inventory for product "+productId,HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseEntity<String> increaseStock(long stock, long productId) {
		logger.info("increase  stock service invoked");
		try {
			Inventory inventory = repository.findByProductId(productId);
			if(stock<1) {
				throw new Exception("New stock less than 1");
			}
			long initialStock = inventory.getStock();
			long newStock = initialStock+stock;
			repository.updateStock(newStock, productId);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Unable to increase stock for product "+productId,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Stock increased for product "+productId,HttpStatus.ACCEPTED);
	}
	
	@Override
	public ResponseEntity<String> decreaseStock(long stock, long productId) {
		logger.info("decrease  stock service invoked");
		try {
			Inventory inventory = repository.findByProductId(productId);
			long initialStock = inventory.getStock();
			long newStock = initialStock-stock;
			if(stock<1) {
				throw new Exception("New stock less than 1");
			}
			if(newStock<0) {
				throw new Exception("Available stock less than required stock");
			}
			repository.updateStock(newStock, productId);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Unable to decrease stock for product "+productId,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("Stock decreased for product "+productId,HttpStatus.ACCEPTED);
	}
	
	
	

}
