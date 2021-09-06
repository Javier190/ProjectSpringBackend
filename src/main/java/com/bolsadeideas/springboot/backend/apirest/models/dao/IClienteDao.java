package com.bolsadeideas.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{ // Aqui van los metodos del lado del Backend.

}



//Crudrepos tiene todos los metodos basicos por defecto ya implementados, findall, save findbyid etc. Ahorra mucho trabajo para la generacion de querys
//Para querys especificas, a traves del nombre del metodo, por debajo se usa SQL nativo
//Por ej si en mi metodo tengo un find, significa que es un select
//O usando notacion @Query(consulta sql) 