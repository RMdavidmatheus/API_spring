package com.samtel.clientes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.samtel.clientes.dto.ClienteDTO;
import com.samtel.clientes.service.ClienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteRestController {
	
	/*
	 * get @GetMapping
	 * post @PostMapping
	 * put @PutMapping
	 * delete @DeleteMapping
	 */
	
	private final ClienteService service;
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> getAll() {
		
		try {
			List<ClienteDTO> reponseData = service.getAll();
			
			if (reponseData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(reponseData, HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> createCliente(@RequestBody @Valid ClienteDTO body){
		try {
			service.crearCliente(body);
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getClientId/{id}")
	public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id)
	{
		try {
			return new ResponseEntity<>(service.getClienteById(id), HttpStatus.OK);
		}
		catch (Exception ex)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// DELETE
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<List<ClienteDTO>> deleteCliente(@PathVariable Long id)
	{
		try{
			service.borrarCliente(id);
			List<ClienteDTO> response = service.getAll();
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PutMapping(value = "/actualizar/{id}")
	public ResponseEntity<List<ClienteDTO>> updateCliente(@PathVariable Long id, @RequestBody @Valid ClienteDTO body){

		try{
			service.actualizarCliente(id,body);
			List<ClienteDTO> response = service.getAll();
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	/*
	 * 
	 * 
	 * @GetMapping("{id}")    http://localhost:9090/clientes/12
	 *   metodo(@PathVariable(name ="id") Long id);
	 *   
	 * @GetMapping("")    http://localhost:9090/clientes?id=12
	 *    metodo(@RequestParam(name ="id") Long id);
	 */
}
