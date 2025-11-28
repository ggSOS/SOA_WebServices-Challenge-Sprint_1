package br.com.fiap.chatbotdatabase1.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.fiap.chatbotdatabase1.dto.AtualizarRespostaDTO;
import br.com.fiap.chatbotdatabase1.dto.ListagemRespostaDTO;
import br.com.fiap.chatbotdatabase1.dto.RespostaDTO;
import br.com.fiap.chatbotdatabase1.model.Resposta;
import br.com.fiap.chatbotdatabase1.repository.RespostaRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/respostas")
@Tag(name = "Resposta", description = "Endpoint para Gerenciamento de Respostas da IA")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Resposta> criarResposta(@RequestBody @Valid RespostaDTO dados, UriComponentsBuilder uriBuilder) {
        Resposta resposta = new Resposta(dados);
        respostaRepository.save(resposta);

        URI uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();

        return ResponseEntity.created(uri).body(resposta);
    }

    @GetMapping
    public ResponseEntity<Page<ListagemRespostaDTO>> listarRespostas(
        @PageableDefault(size = 10, sort = {"id"}) Pageable paginacao
    ) {
        var page = respostaRepository.findAllByAtivoTrue(paginacao).map(ListagemRespostaDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resposta> buscarPorId(@PathVariable Long id) {
        return respostaRepository.findAtivoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Resposta> atualizarResposta(@RequestBody @Valid AtualizarRespostaDTO dados) {
        return respostaRepository.findAtivoById(dados.id())
                .map(resposta -> {
                    resposta.atualizarSatisfacao(dados);
                    return ResponseEntity.ok(resposta);
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
