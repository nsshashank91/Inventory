package com.wipro.lowes.service;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.wipro.lowes.config.InventoryConfiguration;
import com.wipro.lowes.dao.InventoryRepository;
import com.wipro.lowes.entity.Inventory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class InventoryServiceImplTest {
	
	@TestConfiguration
	static class PersonServiceImplTestContextConfiguration {
		@Bean
		public InventoryService inventoryService() {
			return new InventoryServiceImpl();
		}
	}
	
	@Autowired
	private InventoryService service;
	
	@MockBean
	private InventoryConfiguration configuration;
	
	@MockBean
	private InventoryRepository repository;
	
	@Before
	public void setup() {
		Inventory inventory = new Inventory();
		Mockito.when(configuration.getInventoryInstance()).thenReturn(inventory);
		Inventory inventoryBean = new Inventory();
		inventoryBean.setProductId(65);
		inventoryBean.setStock(157);
		Mockito.when(repository.findByProductId(inventoryBean.getProductId())).thenReturn(inventoryBean);
		Mockito.when(repository.save(inventoryBean)).thenReturn(inventoryBean);
		Mockito.when(repository.updateStock(100L,inventoryBean.getProductId())).thenReturn(1);
	}
	
	@Test
	public void testGetStockByProductId() {
		long stock = service.getStockByProductId(65).getBody();
		assertEquals(157,stock);
	}
	
	@Test
	public void testAddInventory() {
		ResponseEntity<String> response = service.addInventory(157, 66);
		HttpStatus statusCode = response.getStatusCode();
		assertEquals(statusCode,HttpStatus.CREATED);
	}
	
	@Test
	public void testIncreaseStock() {
		ResponseEntity<String> response = service.increaseStock(100, 65);
		HttpStatus statusCode = response.getStatusCode();
		assertEquals(statusCode,HttpStatus.ACCEPTED);
	}
	
	@Test
	public void testDecreaseStock() {
		ResponseEntity<String> response = service.decreaseStock(100, 65);
		HttpStatus statusCode = response.getStatusCode();
		assertEquals(statusCode,HttpStatus.ACCEPTED);
	}
	

}
