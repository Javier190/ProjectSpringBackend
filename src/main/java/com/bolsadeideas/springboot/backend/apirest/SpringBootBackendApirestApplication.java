package com.bolsadeideas.springboot.backend.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootBackendApirestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApirestApplication.class, args);
	}
}





//Para correr esta app con spring mas angular hay que bajar mySQL tambien. 
//API REST: La transferencia y envio de data se hace a traves de Endpoints, que es una URL, que envia una peticion
//http al servidor con diferentes metodos, como GET, POST,PUT, DELETE (operaciones crud).

//REST: Transferencia de estado representacional. Es un protocolo entre cliente servidor sin estado.


//Sobre archivo Application.properties:
//OJO CUIDADO CON DEJAR ESPACIOS EN BLANCO
//username root
//password yo la tengo como admin
//Usado para desarrollar se pone create-drop para crear las tablas y se eliminan las tablas cuando se baja del servidor.
//pero por ejemplo update, se crea las tablas una sola vez y luego se actualiza solamente
//logging.level.org.hibernate.SQL= debug es para mostrar las consultas SQL nativas que se generan por debajo

//pAra tener una BD copnectada a nuestro proyecto pasos:
//Crear una BD nueva  mediante consola y terminal de mySQl video 312
//Modificar archivo application.properties y en la Primera linea se le esta dando la direccion URL para conectar la app a la BD
//Crear clase Entity con sus respectivas notaciones
//Y con esos 3 pasos deberiamos tener una BD conectada y mapeada con nuestra entity


