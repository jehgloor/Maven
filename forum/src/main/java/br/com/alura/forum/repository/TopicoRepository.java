package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;
// < qual seria a entidade, qual seria o tipo da chave primaria 
public interface TopicoRepository extends JpaRepository<Topico, Long> {

	// aqui foi mudado pq o nome do curso não esta em topicos 
	// Topicos tem um relacionamento com curso, dai eu puxo nessa "classe"
	List<Topico> findByCursoNome(String nomeCurso);
	
}
