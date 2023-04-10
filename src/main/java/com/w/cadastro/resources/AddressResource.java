package com.w.cadastro.resources;

import java.util.List;

import com.w.cadastro.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.w.cadastro.services.AddressService;

@RestController
@RequestMapping(value = "/addresses")
public class AddressResource {
	
	@Autowired
	private AddressService service;

	@GetMapping
	public ResponseEntity<List<Address>> findaAll(){
		List<Address> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Address> findById(@PathVariable Long id){
		Address obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

}
