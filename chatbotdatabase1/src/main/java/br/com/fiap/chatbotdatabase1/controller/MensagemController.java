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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/mensagens")
@Tag(name = "Mensagem", description = "Endpoint para Gerenciamento de Mensagens de Usuários")
@RequiredArgsConstructor
public class MensagemController {

    private final MensagemRepository mensagemRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<ListagemMensagemDTO> criarMensagem(@RequestBody @Valid MensagemDTO dados, UriComponentsBuilder uriBuilder) {
        Mensagem mensagem = mensagemRepository.save(new Mensagem(dados));
        URI uri = uriBuilder.path("/mensagens/{id}").buildAndExpand(mensagem.getId()).toUri();
        return ResponseEntity.created(uri).body(new ListagemMensagemDTO(mensagem));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemMensagemDTO>> listarMensagens(
            @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao
    ) {
        Page<ListagemMensagemDTO> page = mensagemRepository.findAllByAtivoTrue(paginacao).map(ListagemMensagemDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListagemMensagemDTO> buscarPorId(@PathVariable Long id) {
        return mensagemRepository.findAtivoById(id)
                .map(lista -> ResponseEntity.ok(new ListagemMensagemDTO(lista)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ListagemMensagemDTO> atualizarMensagem(@RequestBody @Valid AtualizarMensagemDTO dados) {
        return mensagemRepository.findAtivoById(dados.id())
                .map(mensagem -> {
                    mensagem.atualizarMensagem(dados);
                    return ResponseEntity.ok(new ListagemMensagemDTO(mensagem));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return mensagemRepository.findAtivoById(id)
                .map(mensagem -> {
                    mensagem.excluir();
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
