package br.com.fiap.chatbot.controller;

import br.com.fiap.chatbot.dto.RespostaDTO;
import br.com.fiap.chatbot.model.Resposta;
import br.com.fiap.chatbot.repository.RespostaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    private final RespostaRepository respostaRepository;

    public RespostaController(RespostaRepository respostaRepository) {
        this.respostaRepository = respostaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Resposta>> listarRespostas() {
        return ResponseEntity.ok(respostaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Resposta> criarResposta(@RequestBody RespostaDTO dto) {
        Resposta resposta = new Resposta(dto.getTexto(), dto.getTema());
        return ResponseEntity.ok(respostaRepository.save(resposta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resposta> buscarPorId(@PathVariable Long id) {
        return respostaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resposta> atualizarResposta(
            @PathVariable Long id,
            @RequestBody RespostaDTO dto) {

        return respostaRepository.findById(id).map(r -> {
            r.setTexto(dto.getTexto());
            r.setTema(dto.getTema());
            return ResponseEntity.ok(respostaRepository.save(r));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!respostaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        respostaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
