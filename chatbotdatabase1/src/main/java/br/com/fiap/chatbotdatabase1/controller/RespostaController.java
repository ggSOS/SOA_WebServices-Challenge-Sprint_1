package br.com.fiap.chatbotdatabase1.controller;

import br.com.fiap.chatbotdatabase1.dto.AtualizarRespostaDTO;
import br.com.fiap.chatbotdatabase1.dto.ListagemRespostaDTO;
import br.com.fiap.chatbotdatabase1.dto.RespostaDTO;
import br.com.fiap.chatbotdatabase1.model.Resposta;
import br.com.fiap.chatbotdatabase1.repository.RespostaRepository;
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
@RequestMapping("/respostas")
@Tag(name = "Resposta", description = "Endpoint para Gerenciamento de Respostas da IA")
@RequiredArgsConstructor
public class RespostaController {

    private final RespostaRepository respostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<ListagemRespostaDTO> criarResposta(@RequestBody @Valid RespostaDTO dados, UriComponentsBuilder uriBuilder) {
        Resposta resposta = respostaRepository.save(new Resposta(dados));
        URI uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new ListagemRespostaDTO(resposta));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemRespostaDTO>> listarRespostas(
        @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao
    ) {
        Page<ListagemRespostaDTO> page = respostaRepository.findAllByAtivoTrue(paginacao).map(ListagemRespostaDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListagemRespostaDTO> buscarPorId(@PathVariable Long id) {
        return respostaRepository.findAtivoById(id)
                .map(resposta -> ResponseEntity.ok(new ListagemRespostaDTO(resposta)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ListagemRespostaDTO> atualizarResposta(@RequestBody @Valid AtualizarRespostaDTO dados) {
        return respostaRepository.findAtivoById(dados.id())
                .map(resposta -> {
                    resposta.atualizarSatisfacao(dados);
                    return ResponseEntity.ok(new ListagemRespostaDTO(resposta));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return respostaRepository.findAtivoById(id)
                .map(resposta -> {
                    resposta.excluir();
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
