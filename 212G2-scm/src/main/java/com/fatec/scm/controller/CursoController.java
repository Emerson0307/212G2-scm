package com.fatec.scm.controller;

import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scm.servico.CursoServico;
import com.fatec.scm.model.Curso;

@Controller
@RequestMapping(path = "/sig")
public class CursoController {
	Logger logger = LogManager.getLogger(CursoController.class);
	
	@Autowired
	CursoServico servico;

	@GetMapping("/cursos")
	public ModelAndView retornaFormDeConsultaTodosCursos() {
		ModelAndView modelAndView = new ModelAndView("consultarCurso");
		modelAndView.addObject("cursos", servico.findAll());
		return modelAndView;
	}

	@GetMapping("/curso")
	public ModelAndView retornaFormDeCadastroDe(Curso curso) {
		ModelAndView mv = new ModelAndView("cadastrarCurso");
		mv.addObject("curso", curso);
		return mv;
	}

	@GetMapping("/cursos/{turma}") // diz ao metodo que ira responder a uma requisicao do tipo get
	public ModelAndView retornaFormParaEditarCurso(@PathVariable("turma") String turma) {
		ModelAndView modelAndView = new ModelAndView("atualizarCurso");
		modelAndView.addObject("curso", servico.findByTurma(turma)); // o repositorio e injetado no controller
		return modelAndView; // addObject adiciona objetos para view
	}

	@GetMapping("/curso/{id}")
	public ModelAndView excluirNoFormDeConsultaCurso(@PathVariable("id") Long id) {
		servico.deleteById(id);
		logger.info(">>>>>> 1. servico de exclusao chamado para o id => " + id);
		ModelAndView modelAndView = new ModelAndView("consultarCurso");
		modelAndView.addObject("cursos", servico.findAll());
		return modelAndView;
	}

	@PostMapping("/cursos")
	public ModelAndView save(@Valid Curso curso, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarCurso");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarCurso");
		} else {
			modelAndView = servico.saveOrUpdate(curso);
		}
		return modelAndView;
	}

	@PostMapping("/cursos/{id}")
	public ModelAndView atualizaCurso(@PathVariable("id") Long id, @Valid Curso curso, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarCurso");
		if (result.hasErrors()) {
			curso.setId(id);
			return new ModelAndView("atualizarCurso");
		}
		
// programacao defensiva - deve-se verificar se o Curso existe antes de atualizar
		Curso umCurso = servico.findById(id);
		umCurso.setNivel(curso.getNivel());
		umCurso.setTurma(curso.getTurma());
		umCurso.setTurno(curso.getTurno());
		umCurso.setProfessor(curso.getProfessor());
		modelAndView = servico.saveOrUpdate(umCurso);
		return modelAndView;
	}
}