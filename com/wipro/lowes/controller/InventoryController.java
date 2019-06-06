package com.wipro.lowes.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.lowes.dto.ProductStock;
import com.wipro.lowes.service.InventoryService;

@RestController
@RequestMapping(value="/meru")
public class InventoryController {

	@Autowired
	private InventoryService service;
	
	private Logger logger = Logger.getLogger("InventoryController");
	
	
	@RequestMapping(value="/getStock/{productId}")
	public ResponseEntity<Long> getStockByProductId(@PathVariable Long productId) {
		logger.info("get stock by product for id "+productId+" invoked");
		try {
			if(productId!=null) {
				return service.getStockByProductId(productId);
			}
			else {
				throw new NullPointerException("Product Id is Null");
			}
			
		}
		catch(Exception e) {
			return new ResponseEntity<Long>(-1L,HttpStatus.BAD_REQUEST); 
		}
		
	}
	
	@RequestMapping(value="/increaseStock", method = RequestMethod.PUT)
	public ResponseEntity<String> increaseStock(@RequestBody ProductStock productStock) {
		try {
			Long stock = productStock.getStock();
			Long productId = productStock.getProductId();
			if(stock==null || productId==null) {
				throw new NullPointerException("Stock or Product Id is Null");
			}
			else {
				return service.increaseStock(stock, productId);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Please specify stock and product id",HttpStatus.BAD_REQUEST); 
		}
		
	}
	
	@RequestMapping(value="/decreaseStock", method = RequestMethod.PUT)
	public ResponseEntity<String> decreaseStock(@RequestBody ProductStock productStock) {
		try {
			Long stock = productStock.getStock();
			Long productId = productStock.getProductId();
			if(stock==null || productId==null) {
				throw new NullPointerException("Stock or Product Id is Null");
			}
			else {
				return service.decreaseStock(stock, productId);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Please specify stock and product id",HttpStatus.BAD_REQUEST); 
		}
		
	}
	
	@RequestMapping(value="/addStock", method = RequestMethod.POST)
	public ResponseEntity<String> addStock(@RequestBody ProductStock productStock) {
		try {
			Long stock = productStock.getStock();
			Long productId = productStock.getProductId();
			if(stock==null || productId==null) {
				throw new NullPointerException("Stock or Product Id is Null");
			}
			else if(stock <0 ||productId<0) {
				throw new Exception("Please specify stock and Product id correctly");
			}
			else {
				return service.addInventory(stock, productId);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Please specify stock and product id",HttpStatus.BAD_REQUEST); 
		}
		 
	}
}
