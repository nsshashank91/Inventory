package com.wipro.lowes.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wipro.lowes.entity.Inventory;

@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long>{
	
	Inventory findByProductId(long prooductId);
	
	@Modifying
	@Query("UPDATE Inventory as i SET i.stock=:stock where i.productId=:productId")
	int updateStock(@Param("stock") Long stock, @Param("productId") Long productId);

}
