package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;


@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	//private Topico Topico one;
	
	//@RequestMapping(value = "/topicos", method = RequestMethod.GET)
	// aqui é para eu dizer qual é metodo que vai ser utilizado :
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso){
		if(nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		}else {
			// aqui seria se não tiver nenhum curso da URL mostra todos
			//se usar assim ele consegue gerar uma Query automaticamente 
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
		
		}
	}
	//@RequestMapping(value = "/topicos", method = RequestMethod.POST)
	// dai é interessante criar um codigo 201 para esse codigo é o melhor
	// e para retornar esse codigo o methodo nao pode ser void, tem ser esse que vai estar escrito ai 
	// e ele vai retornar um Topico dto , que é na onde esta minhas informações
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		// precisa converter para topico mesmo
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		// para criar com o status 200 tem uns metodos estaticos , como vc vai ver a seguir :
		// se eu colocasse o ResponseEntity.ok ir trazer o status generico
		// dai para retornar o 201 tem que usar o metodo created e passar a uri como params 
		// e tb tem q retornar o cabeçalho com um metodo chamado location
		// dai só passar o UriComponentsBuilder uriBuilder como params
		//dai coloca o id entre chaves para ser dinamico.
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		// se por acaso for procurar um id que nao existe , da um erro muito doido 
		// e esse erro se da exatamente nessa linha, lançando uma exceção
		// dai bora fazer um tratamento de exceção
		// antes estava assim :Topico topico = topicoRepository.getReferenceById(id);
		// e o nome do metodo estava assim : public DetalhesDoTopicoDto detalhar(@PathVariable Long id)
		//por conta de mudar o return de responseEntity teve que mudar a propriedade do metodo tb

		
		Optional <Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get())) ;
		}
		return ResponseEntity.notFound().build();
		
	}
	// o put meio q é pra sobreescrever o recurso inteiro 
	// o patch é fazer uma pequena atualização
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,@RequestBody @Valid AtualizacaoTopicoForm form){
		
		// logica para atualização
		// ta chegando o id do banco de dados e vai vir com as informação
		//dai eu tenho q sobreescrever
		// antes :Topico topico = form.atualizar(id, topicoRepository);
		//return ResponseEntity.ok(new TopicoDto(topico));
		
		Optional <Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id){
		// antes: topicoRepository.deleteById(id);
		//return ResponseEntity.ok().build();
		Optional <Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
		
	
		
}
