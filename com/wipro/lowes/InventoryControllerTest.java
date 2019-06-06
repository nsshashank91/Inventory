package com.wipro.lowes.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.wipro.lowes.dto.ProductStock;
import com.wipro.lowes.service.InventoryService;


@RunWith(SpringRunner.class)
@WebMvcTest(InventoryController.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private InventoryService service;
	
	
	@Test
	public void testGetStockByProductId() throws Exception {
		Long stock=160L;
		ResponseEntity<Long> response = new ResponseEntity<Long>(stock,HttpStatus.OK);
		given(service.getStockByProductId(12)).willReturn(response);
		mvc.perform(
				get("/meru/getStock/12")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.is(160)));
					
	}
	
	@Test
	public void addStockTest() throws Exception{
		ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.CREATED);
		given(service.addInventory(150, 25)).willReturn(response);
		JSONObject inv = new JSONObject();
		inv.put("stock", 150);
		inv.put("productId", 25);
		
		mvc
	    .perform(post("/meru/addStock")
	    .accept(MediaType.APPLICATION_JSON)
	    .content(inv.toString())
	    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
	}
	
	@Test
	public void increaseStockTest() throws Exception{
		ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.ACCEPTED);
		given(service.increaseStock(120,50)).willReturn(response);
		JSONObject inv = new JSONObject();
		inv.put("stock", 150);
		inv.put("productId", 50);
		mvc
	    .perform(put("/meru/increaseStock")
	    .accept(MediaType.APPLICATION_JSON)
	    .content(inv.toString())
	    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
	}
	
	@Test
	public void decreaseStockTest() throws Exception{
		ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.ACCEPTED);
		given(service.decreaseStock(120,50)).willReturn(response);
		JSONObject inv = new JSONObject();
		inv.put("stock", 150);
		inv.put("productId", 50);
		mvc
	    .perform(put("/meru/decreaseStock")
	    .accept(MediaType.APPLICATION_JSON)
	    .content(inv.toString())
	    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
	}

}
