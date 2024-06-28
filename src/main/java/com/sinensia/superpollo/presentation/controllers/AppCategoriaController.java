package com.sinensia.superpollo.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sinensia.superpollo.business.model.Categoria;
import com.sinensia.superpollo.business.services.CategoriaServices;

@Controller
@RequestMapping("/app")
public class AppCategoriaController {
	
	@Autowired
	private CategoriaServices categoriaServices;

	@GetMapping("/categorias") // CONTROLADOR
	public ModelAndView getPaginaCategorias(ModelAndView mav) {
		
		List<Categoria> categorias = categoriaServices.getAll();
		
		mav.addObject("categorias", categorias);   // MODELO
		mav.setViewName("listado-categorias");     // VISTA
		
		return mav;
	}
	
}
