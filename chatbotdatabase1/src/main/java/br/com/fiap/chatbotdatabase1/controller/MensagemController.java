package br.com.fiap.chatbotdatabase1.controller;

import br.com.fiap.chatbotdatabase1.dto.AtualizarMensagemDTO;
import br.com.fiap.chatbotdatabase1.dto.ListagemMensagemDTO;
import br.com.fiap.chatbotdatabase1.dto.MensagemDTO;
import br.com.fiap.chatbotdatabase1.model.Mensagem;
import br.com.fiap.chatbotdatabase1.repository.MensagemRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/mensagens")
@Tag(name = "Mensagem", description = "Endpoint para Gerenciamento de Mensagens de Usuários")
public class MensagemController {

    @Autowired
    private MensagemRepository mensagemRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Mensagem> criarMensagem(@RequestBody @Valid MensagemDTO dados, UriComponentsBuilder uriBuilder) {
        Mensagem mensagem = new Mensagem(dados);
        mensagemRepository.save(mensagem);

        URI uri = uriBuilder.path("/mensagens/{id}").buildAndExpand(mensagem.getId()).toUri();

        return ResponseEntity.created(uri).body(mensagem);
    }

    @GetMapping
    public ResponseEntity<Page<ListagemMensagemDTO>> listarMensagens(
            @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao
    ) {
        Page<ListagemMensagemDTO> page = mensagemRepository.findAllByAtivoTrue(paginacao).map(ListagemMensagemDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> buscarPorId(@PathVariable Long id) {
        return mensagemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Mensagem> atualizarMensagem(@RequestBody @Valid AtualizarMensagemDTO dados) {
        return mensagemRepository.findById(dados.id())
                .map(mensagem -> {
                    mensagem.atualizarMensagem(dados);
                    // O save não é necessário aqui devido ao @Transactional
                    return ResponseEntity.ok(mensagem);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return mensagemRepository.findById(id)
                .map(mensagem -> {
                    mensagem.excluir();
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
