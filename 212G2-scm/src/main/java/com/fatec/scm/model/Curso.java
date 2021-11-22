package com.fatec.scm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.validation.constraints.*;

@Entity
public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min = 2, max = 2)
	private String turma;
	
	@NotNull
	@Size(min = 1, max = 50, message = "Só existe três níveis: Básico, Intermediário e Avançado")
	private String nivel;
	
	@NotNull
	@Size(min = 5, max = 5, message = "Digite o turno: Manha, Tarde ou Noite")
	private String turno;

	@NotNull
	@Size(min = 1, max = 50, message = "Digite o nome do professor que ministra as aulas")
	private String professor;
	
	public Curso() {}
	public Curso (String nivel, String turma, String turno, String professor) {
		super();
		this.nivel=nivel;
		this.turma=turma;
		this.turno=turno;
		this.professor=professor;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}
}