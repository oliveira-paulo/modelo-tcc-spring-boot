package com.algaworks.vinhos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.vinhos.model.Vinho;

public interface VinhosReposirory extends JpaRepository<Vinho, Long> {

	public Optional<Vinho> findByNomeIgnoreCase(String nome);
}
