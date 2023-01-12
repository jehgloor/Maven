package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	// aqui foi mudado pq o nome do curso não esta em topicos 
	// Topicos tem um relacionamento com curso, dai eu puxo nessa "classe"
	//List<Topico> findByCursoNome(String nomeCurso);

	Curso findByNome(String nomeCurso);
	
}

