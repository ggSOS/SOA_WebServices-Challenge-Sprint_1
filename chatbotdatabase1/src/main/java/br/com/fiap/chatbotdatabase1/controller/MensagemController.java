package br.com.fiap.chatbotdatabase1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.chatbotdatabase1.dto.AtualizarMensagemDTO;
import br.com.fiap.chatbotdatabase1.dto.ListagemMensagemDTO;
import br.com.fiap.chatbotdatabase1.dto.MensagemDTO;
import br.com.fiap.chatbotdatabase1.model.Mensagem;
import br.com.fiap.chatbotdatabase1.repository.MensagemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/mensagens")
@Tag(name = "Mensagens", description = "Gerenciamento de mensagens do chatbot")
public class MensagemController {

    @Autowired
    private MensagemRepository mensagemRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar nova mensagem", description = "Cria uma nova mensagem no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Mensagem criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ListagemMensagemDTO> cadastrar(
            @RequestBody @Valid MensagemDTO dados,
            UriComponentsBuilder uriBuilder) {
        
        var mensagem = new Mensagem(dados);
        mensagemRepository.save(mensagem);
        
        var uri = uriBuilder.path("/mensagens/{id}").buildAndExpand(mensagem.getId()).toUri();
        return ResponseEntity.created(uri).body(new ListagemMensagemDTO(mensagem));
    }

    @GetMapping
    @Operation(summary = "Listar mensagens", description = "Lista todas as mensagens ativas com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<Page<ListagemMensagemDTO>> listar(
            @Parameter(description = "Número da página (começa em 0)") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") 
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable paginacao = PageRequest.of(page, size);
        var pageResult = mensagemRepository.findAllByAtivoTrue(paginacao)
                .map(ListagemMensagemDTO::new);
        
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar mensagem", description = "Busca uma mensagem específica por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem encontrada"),
        @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    public ResponseEntity<ListagemMensagemDTO> detalhar(
            @Parameter(description = "ID da mensagem") 
            @PathVariable Long id) {
        var mensagem = mensagemRepository.findById(id);
        
        if (mensagem.isPresent() && mensagem.get().getAtivo()) {
            return ResponseEntity.ok(new ListagemMensagemDTO(mensagem.get()));
        }
        
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar mensagem", description = "Atualiza uma mensagem existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    public ResponseEntity<ListagemMensagemDTO> atualizar(
            @RequestBody @Valid AtualizarMensagemDTO dados) {
        
        var mensagem = mensagemRepository.findById(dados.id());
        
        if (mensagem.isPresent() && mensagem.get().getAtivo()) {
            var mensagemAtualizada = mensagem.get();
            mensagemAtualizada.atualizarMensagem(dados);
            return ResponseEntity.ok(new ListagemMensagemDTO(mensagemAtualizada));
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Excluir mensagem", description = "Realiza exclusão lógica da mensagem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Mensagem excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da mensagem") 
            @PathVariable Long id) {
        var mensagem = mensagemRepository.findById(id);
        
        if (mensagem.isPresent() && mensagem.get().getAtivo()) {
            mensagem.get().excluir();
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}