package com.bolsadeideas.springboot.backend.apirest.models.services;

import java.util.List;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

//Esta interfaz tiene el contrato de implementacion, los metodos del CRUD. Aqui van los metodos del lado del controller.
//Esta Interfaz es la que se Inyecta cuando se quiere hacer las consultas.
public interface IClienteService {
	
	public List<Cliente> findAll();
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);
	
	

}
