package com.wipro.lowes.dao;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.wipro.lowes.entity.Inventory;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private InventoryRepository repository; 
	
	
	
	@Test
	public void testFindByProductId() {
		Inventory inv = new Inventory();
		inv.setProductId(11);
		inv.setStock(100);
		entityManager.persist(inv);
		Inventory inventory = repository.findByProductId(11);
		assertThat(inventory).isEqualTo(inv);
	}
	
	@Test
	public void updateStock() {
		Inventory inv = new Inventory();
		inv.setProductId(11);
		inv.setStock(100);
		entityManager.persist(inv);
		int newStock = repository.updateStock(100L, 11L);
		assertEquals(1,newStock);
	}

}
