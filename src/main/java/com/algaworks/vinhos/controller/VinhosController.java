package com.algaworks.vinhos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.algaworks.vinhos.model.TipoVinho;
import com.algaworks.vinhos.model.Vinho;
import com.algaworks.vinhos.repository.VinhosReposirory;
import com.algaworks.vinhos.service.CadastroVinhoService;
import com.algaworks.vinhos.service.exception.NomeVinhoJaCadastradoException;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {

	@Autowired
	private VinhosReposirory vinhosReposirory;

	@Autowired
	private CadastroVinhoService cadastroVinhoService;

	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		vinhosReposirory.delete(id);
		
		attributes.addFlashAttribute("mensagem", "Vinho removido com sucesso!");
		
		return "redirect:/vinhos";
	}


	@GetMapping
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("vinhos/lista-vinhos");
		modelAndView.addObject("vinhos", vinhosReposirory.findAll());
		
		return modelAndView;
	}


	@GetMapping("/novo")
	public ModelAndView novo(Vinho vinho) {
		ModelAndView modelAndView = new ModelAndView("vinhos/cadastro-vinho");
		
		modelAndView.addObject(vinho);
		modelAndView.addObject("tipos", TipoVinho.values());		
		
		return modelAndView;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Vinho vinho, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(vinho);
		}
		try {
			cadastroVinhoService.salvar(vinho);
		} catch (NomeVinhoJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(vinho);
		}
		attributes.addFlashAttribute("mensagem", "Vinho salvo com sucesso!");

		return new ModelAndView("redirect:/vinhos/novo");
	}

	@GetMapping("/update/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		return novo(vinhosReposirory.findOne(id));
		
	}

	
}
