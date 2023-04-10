package com.w.cadastro.services;

import java.util.List;
import java.util.Optional;

import com.w.cadastro.entities.Address;
import com.w.cadastro.entities.User;
import com.w.cadastro.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository repository;
	
	public List<Address> findAll(){
		return repository.findAll();
	}
	
	public Address findById(Long id) {
		Optional<Address> obj = repository.findById(id);
		return obj.get();
	}

	public Address insert(Address obj) {
		return repository.save(obj);
	}

}
