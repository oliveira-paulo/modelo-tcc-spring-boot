package com.algaworks.vinhos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.vinhos.model.Vinho;
import com.algaworks.vinhos.repository.VinhosReposirory;
import com.algaworks.vinhos.service.exception.NomeVinhoJaCadastradoException;

@Service
public class CadastroVinhoService {

	@Autowired
	private VinhosReposirory vinhosReposirory;
	
	@Transactional
	public Vinho salvar(Vinho vinho) {
		Optional<Vinho> vinhoExistente = vinhosReposirory.findByNomeIgnoreCase(vinho.getNome());
		
		if (vinhoExistente.isPresent() && !vinhoExistente.get().equals(vinho)) {
			throw new NomeVinhoJaCadastradoException("Nome do vinho já cadastrado");
		}
		
		return vinhosReposirory.saveAndFlush(vinho);
	}
	
}
