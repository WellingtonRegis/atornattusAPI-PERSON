package com.w.cadastro.resources;

import java.net.URI;
import java.util.List;

import com.w.cadastro.entities.Address;
import com.w.cadastro.entities.User;
import com.w.cadastro.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.w.cadastro.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@GetMapping
	public ResponseEntity<List<User>> findaAll(){
		List<User> list = userService.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id){
		User obj = userService.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User obj){

		User user = new User(null, obj.getNomeCompleto(), obj.getDataNascimento());
		user = userService.insert(user);

		for (int i = 0; i < obj.getEnderecos().size(); i++) {
			Address data = obj.getEnderecos().get(i);
			Address address = new Address(
					null,
					data.getLogradouro(),
					data.getCep(),
					data.getNumero(),
					data.getCidade(),
					data.isPrincipal(),
					user.getId()
			);

			addressService.insert(address);
		}

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@PostMapping(value = "/{id}")
	public ResponseEntity<User> insert(@RequestBody User obj, @PathVariable Long id){
		User userFromDatabase = userService.findById(id);
		if (userFromDatabase == null) {
			return ResponseEntity.notFound().build();
		}

		for (int i = 0; i < obj.getEnderecos().size(); i++) {
			Address data = obj.getEnderecos().get(i);
			Address address = new Address(
					null,
					data.getLogradouro(),
					data.getCep(),
					data.getNumero(),
					data.getCidade(),
					data.isPrincipal(),
					userFromDatabase.getId()
			);

			addressService.insert(address);
		}

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj){
		obj = userService.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}


}
