package com.spring.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.entity.User;
import com.spring.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;
	
	@Override
	public Iterable<User> getAllUsers() {
		return repository.findAll();
	}
	
	private boolean checkEmailAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByEmail(user.getEmail());
		if(userFound.isPresent()) {
			throw new Exception("La direccion de correo ya se encuentra registrada");
		}
		return true;
	}
	
	private boolean checkRfcAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByRfc(user.getRfc());
		if(userFound.isPresent()) {
			throw new Exception("El RFC ya se encuentra registrado");
		}
		return true;
	}
	
	private boolean checkPasswordValid(User user) throws Exception {
		if (!user.getPassword().equals(user.getConfirmPassword())) {
			throw new Exception("Las contraseñas no coinciden");
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {
		if(checkEmailAvailable(user) && checkRfcAvailable(user)) {
			repository.save(user);
		}
		return null;
	}
	
	@Override
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getId());
		mapUser(fromUser, toUser);
		return repository.save(toUser);
	}
	
	@Override
	public User updateUserSinId(User fromUser) throws Exception {
		User toUser = getUserByEmail(fromUser.getEmail());
		mapUserSinId(fromUser, toUser);
		return repository.save(toUser);
	}
	

	protected void mapUser(User from,User to) {
		to.setNombre(from.getNombre());
		to.setRfc(from.getRfc());
		to.setTelefono(from.getTelefono());
		to.setDireccion(from.getDireccion());
		to.setWebsite(from.getWebsite());
		to.setPassword(from.getPassword());
	}
	
	protected void mapUserSinId(User from,User to) {
		to.setNombre(from.getNombre());
		to.setRfc(from.getRfc());
		to.setTelefono(from.getTelefono());
		to.setDireccion(from.getDireccion());
		to.setWebsite(from.getWebsite());
	}
	
	@Override
	public User getUserById(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new Exception("El usuario no existe"));
		
	}
	
	public User getUserByEmail(String email) throws Exception {
		Optional<User> userFound = repository.findByEmail(email);
		
		return userFound.get();
		
	}
	
	public void deleteUser(Long id) throws Exception {
		User user = getUserById(id);

		repository.delete(user);
	}

}
