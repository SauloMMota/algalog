package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.assembler.EntregaAssembler;
import com.algaworks.algalog.api.model.EntregaModel;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.input.EntregaInput;
import com.algaworks.algalog.domain.repository.EntregaRepository;
import com.algaworks.algalog.domain.service.FinalizacaoEntregaService;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {
	
	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaRepository entregaRepository;
	private EntregaAssembler entregaAssembler;
	private FinalizacaoEntregaService finalizacaoEntregaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaModel criar(@Valid @RequestBody EntregaInput entregaInput) {
		Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
		return entregaAssembler.toModel(solicitacaoEntregaService.solicitar(novaEntrega));
	}
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
		finalizacaoEntregaService.finalizar(entregaId);
	}
	
	@GetMapping
	public List<EntregaModel> listar() {
		return  entregaAssembler.toCollectionModel(entregaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntregaModel> buscar(@PathVariable Long id) {
		return entregaRepository.findById(id)
				.map(entrega -> {
					EntregaModel entregaModel = entregaAssembler.toModel(entrega);
//					EntregaModel entregaModel = new EntregaModel();
//					entregaModel.setId(entrega.getId());
//					entregaModel.setNomeCliente(entrega.getCliente().getNome());
//					entregaModel.setDestinatario(new DestinatarioModel());
//					entregaModel.getDestinatario().setNome(entrega.getDestinatario().getNome());
//					entregaModel.getDestinatario().setLogradouro(entrega.getDestinatario().getLogradouro());
//					entregaModel.getDestinatario().setNumero(entrega.getDestinatario().getNumero());
//					entregaModel.getDestinatario().setComplemento(entrega.getDestinatario().getComplemento());
//					entregaModel.getDestinatario().setBairro(entrega.getDestinatario().getBairro());
//					entregaModel.setTaxa(entrega.getTaxa());
//					entregaModel.setStatus(entrega.getStatus());
//					entregaModel.setDataPedido(entrega.getDataPedido());
//					entregaModel.setDataFinalizacao(entrega.getDataFinalizacao());
					return ResponseEntity.ok(entregaModel);
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
