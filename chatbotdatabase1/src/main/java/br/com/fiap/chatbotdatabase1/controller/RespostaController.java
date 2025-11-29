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

import br.com.fiap.chatbotdatabase1.dto.AtualizarRespostaDTO;
import br.com.fiap.chatbotdatabase1.dto.ListagemRespostaDTO;
import br.com.fiap.chatbotdatabase1.dto.RespostaDTO;
import br.com.fiap.chatbotdatabase1.model.Resposta;
import br.com.fiap.chatbotdatabase1.repository.RespostaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respostas")
@Tag(name = "Respostas", description = "Gerenciamento de respostas do chatbot")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Cadastrar nova resposta", description = "Cria uma nova resposta no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Resposta criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<ListagemRespostaDTO> cadastrar(
            @RequestBody @Valid RespostaDTO dados,
            UriComponentsBuilder uriBuilder) {
        
        var resposta = new Resposta(dados);
        respostaRepository.save(resposta);
        
        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new ListagemRespostaDTO(resposta));
    }

    @GetMapping
    @Operation(summary = "Listar respostas", description = "Lista todas as respostas ativas com paginação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<Page<ListagemRespostaDTO>> listar(
            @Parameter(description = "Número da página (começa em 0)") 
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") 
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable paginacao = PageRequest.of(page, size);
        var pageResult = respostaRepository.findAllByAtivoTrue(paginacao)
                .map(ListagemRespostaDTO::new);
        
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar resposta", description = "Busca uma resposta específica por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resposta encontrada"),
        @ApiResponse(responseCode = "404", description = "Resposta não encontrada")
    })
    public ResponseEntity<ListagemRespostaDTO> detalhar(
            @Parameter(description = "ID da resposta") 
            @PathVariable Long id) {
        var resposta = respostaRepository.findAtivoById(id);
        
        if (resposta.isPresent()) {
            return ResponseEntity.ok(new ListagemRespostaDTO(resposta.get()));
        }
        
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    @Transactional
    @Operation(summary = "Atualizar resposta", description = "Atualiza a satisfação de uma resposta existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resposta atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Resposta não encontrada")
    })
    public ResponseEntity<ListagemRespostaDTO> atualizar(
            @RequestBody @Valid AtualizarRespostaDTO dados) {
        
        var resposta = respostaRepository.findAtivoById(dados.id());
        
        if (resposta.isPresent()) {
            var respostaAtualizada = resposta.get();
            respostaAtualizada.atualizarSatisfacao(dados);
            return ResponseEntity.ok(new ListagemRespostaDTO(respostaAtualizada));
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Excluir resposta", description = "Realiza exclusão lógica da resposta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Resposta excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Resposta não encontrada")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID da resposta") 
            @PathVariable Long id) {
        var resposta = respostaRepository.findAtivoById(id);
        
        if (resposta.isPresent()) {
            resposta.get().excluir();
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}