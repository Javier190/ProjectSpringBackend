package com.bolsadeideas.springboot.backend.apirest.controllers;


import java.util.HashMap;

//Creando Api Rest, que es una URL para poder conectar y enviar datos y peticiones a nuestra app 
//por ejemplo para listar clientes en nuestra app con angular.

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;
																													//CORS: Intercambio de recursos de origen cruzado, Es un mecanismo de control de acceso HTTP para acceder //a ciertos recursos.  Determina si se permite compartir recursos en una solicitud tipo Get o Post.//Lo usaremos para conectar nuestro front (angular),  con API REST con spring (Backend).//CORS interactua entre navegador web y servidor para determinar si es seguro permitir las peticiones de origen cruzado
@CrossOrigin(origins = { "http://localhost:4200" })									//Aca ponemos el puerto que usa Angular Y Listo ya estan conectadas spring con angular
@RestController
@RequestMapping("/api")																			//esta es la ruta base o de primer nivel
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;   														//se inyecta la inteerfaz porque busco que esta interfaz ya esta implementada en ClienteServiceImpl

	@GetMapping("/clientes")					 													//Es una peticion tipo Get 
	public List<Cliente> index() {
		return clienteService.findAll();
	}
	
	@GetMapping("/clientes/{id}")																												//recuperando id por URL a traves del pathvariable y pasandole como parametro al metodo
	@ResponseStatus(HttpStatus.OK) 
	public ResponseEntity<?> show(@PathVariable Long id) {																	//ResponseEntity(antes devolvia un Cliente, ahora es ResponseEnt) Nos permitira pasar nuestro objeto Cliente y ademas permite el manejo de eerrores
	Cliente cliente = null;																																//En caso de ocurrir algun error por el lado del servidor, es mejor usar un try Catch
	Map<String, Object> response = new HashMap<>();																			//se declara mapa nomas	
	
	try { cliente = clienteService.findById(id);  } 
		
		catch (DataAccessException e) {																											//Aqui va codigo para manejar un error en conexion a BD
			
			response.put("mensaje", "Error en conexion a Base de Datos !");													//Tambien se podria agregar el error de la exception "e", pero no es requerido	
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		
	if(cliente == null) {
		response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" No existe en la Base de Datos")));		//se llena el mapa en caso de que ID no exista
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 							//respuesta de eerror
	}
	
	return new ResponseEntity<Cliente>(cliente, HttpStatus.OK); 																			//Aca se esta pasando al response el objeto cliente y el status
	}
	
	
	@PostMapping("/clientes")
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {      																//Este dato se envia desde el cliente (angular) hacia el servidor, por lo que viene en formato JSON, por eso se pone requestBody
		Cliente clienteNew = null;	
		Map<String, Object> response = new HashMap<>();	
		try {
		 clienteNew = clienteService.save(cliente);
		
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al crear un nuevo cliente");
			response.put("error", e.getMessage().concat("> ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR ); 
		}
			
		response.put("mensaje", "El cliente ha sido creado con exito!");
		response.put("cliente", clienteNew);
																																								//cliente.setCreateAt(new Date());  //Se puede poner aqui o como metodo PrePersist
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED ); 				//Aca estamos pasando a travesde un map, tanto el cliente nuevo creado, como un mensaje para el usuario que mande msje de que se creo con exito
		
	}	
	
	@PutMapping("/clientes/{id}")    																										//Post es para crear agregar, put es para actualizar
	public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {			//Cliente cliente se envia a la BD desde el front (como JSON), pero para eso, este cliente hay que recuperarlo desde la BD a traves del ID y luego se modifica sus datos
		Cliente clienteActual = clienteService.findById(id);																			//Video 323 por si quieres repasar lo de requestbody
		Cliente clienteUpdated = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(clienteActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: ".concat(id.toString().concat(" No existe en la Base de Datos")));		//se llena el mapa en caso de que ID no exista
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 							//respuesta de eerror
		}
		
		try {
		clienteActual.setNombre(cliente.getNombre());																				//cliente actual es el cliente desde la BD y el cliente es lo que queremos actualizar
		clienteActual.setApellido(cliente.getApellido());
		clienteActual.setEmail(cliente.getEmail());
		clienteActual.setCreateAt(cliente.getCreateAt());
		
		 clienteUpdated =  clienteService.save(clienteActual); 
		 }
		
		 catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar en la basde de datos");
				response.put("error", e.getMessage().concat("> ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR ); 
			}
		response.put("mensaje", "El cliente ha sido actualizado con exito!");
		response.put("cliente", clienteUpdated);
		
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			clienteService.delete(id);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cliente en la basde de datos");
			response.put("error", e.getMessage().concat("> ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR ); 
		}
		response.put("mensaje", "El cliente ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK ); 
		
		
		
		
	}
	
}

//Para manejo de errores primero tenemos que manejar desde el backend y luego en angular. 












