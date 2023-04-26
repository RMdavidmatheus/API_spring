package com.samtel.clientes.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samtel.clientes.dto.ClienteDTO;
import com.samtel.clientes.persistence.entity.ClienteEntity;
import com.samtel.clientes.persistence.repository.ClienteRepository;
import com.samtel.clientes.service.ClienteService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

	private final ClienteRepository repository;
	
	@Override
	public List<ClienteDTO> getAll() {		
		return repository.findAll().stream().map(ent -> {
			ClienteDTO dto = new ClienteDTO();
			dto.setId(ent.getId());
			dto.setNombreCliente(ent.getNombre());
			dto.setCorreoElectronico(ent.getCorreo());
			dto.setDocumento(ent.getDni());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public ClienteDTO getClienteById(Long id) {
		return repository.findById(id).map(ent -> {
			ClienteDTO dto = new ClienteDTO();
			dto.setId(ent.getId());
			dto.setNombreCliente(ent.getNombre());
			dto.setDocumento(ent.getDni());
			dto.setCorreoElectronico(ent.getCorreo());
			return dto;
		}).get();
	}

	@Override
	public ClienteDTO crearCliente(ClienteDTO cliente) {
		ClienteEntity entity = new ClienteEntity();
		
		entity.setNombre(cliente.getNombreCliente());
		entity.setDni(cliente.getDocumento());
		entity.setCorreo(cliente.getCorreoElectronico());
		
		repository.save(entity);
		
		cliente.setId(entity.getId());
		return cliente;
	}

	@Override
	public ClienteDTO actualizarCliente(Long id, ClienteDTO cliente) {
		ClienteEntity updateClient = repository.findById(id).map(ent -> {
			ClienteEntity entidad = new ClienteEntity();
			entidad.setNombre(ent.getNombre());
			entidad.setDni(ent.getDni());
			entidad.setCorreo(ent.getCorreo());
			entidad.setId(ent.getId());
			return entidad;
		}).get();
		updateClient.setNombre(cliente.getNombreCliente());
		updateClient.setDni(cliente.getDocumento());
		updateClient.setCorreo(cliente.getCorreoElectronico());
		repository.save(updateClient);
		return cliente;
	}

	@Override
	public void borrarCliente(Long id) {
		ClienteDTO cliente = repository.findById(id).map(ent -> {
			ClienteDTO dto = new ClienteDTO();
			dto.setId(ent.getId());
			dto.setNombreCliente(ent.getNombre());
			dto.setDocumento(ent.getDni());
			dto.setCorreoElectronico(ent.getCorreo());
			return dto;
		}).get();

		// LLENAMOS EL ENTITY CLIENTE CON EL DTO
		ClienteEntity entity = new ClienteEntity();
		entity.setId(cliente.getId());
		entity.setNombre(cliente.getNombreCliente());
		entity.setDni(cliente.getDocumento());
		entity.setCorreo(cliente.getCorreoElectronico());
		repository.delete(entity);
	}

}
