package com.fatec.scm.servico;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import com.fatec.scm.model.Curso;
import com.fatec.scm.model.CursoRepository;

@Service
public class CursoServicoI implements CursoServico {
	Logger logger = LogManager.getLogger(CursoServicoI.class);

	@Autowired
	private CursoRepository cursoRepository;

	public Iterable<Curso> findAll() {
		return cursoRepository.findAll();
	}

	public Curso findByTurma(String turma) {
		return cursoRepository.findByTurma(turma);
	}

	public void deleteById(Long id) {
		cursoRepository.deleteById(id);
		logger.info(">>>>>> 2. comando exclusao executado para o id => " + id);
	}

	public Curso findById(Long id) {
		return cursoRepository.findById(id).get();
	}

	public ModelAndView saveOrUpdate(Curso curso) {
		ModelAndView modelAndView = new ModelAndView("consultarCurso");
		try {
			cursoRepository.save(curso);
			logger.info(">>>>>> 4. comando save executado  ");
			modelAndView.addObject("cursos", cursoRepository.findAll());
		} catch (Exception e) {
			modelAndView.setViewName("cadastrarCurso");
			if (e.getMessage().contains("could not execute statement")) {
				modelAndView.addObject("message", "Dados invalidos - curso já cadastrado.");
				logger.info(">>>>>> 5. curso ja cadastrado ==> " + e.getMessage());
			} else {
				modelAndView.addObject("message", "Erro não esperado - contate o administrador");
				logger.error(">>>>>> 5. erro nao esperado ==> " + e.getMessage());
			}
		}
		return modelAndView;
	}
	
}