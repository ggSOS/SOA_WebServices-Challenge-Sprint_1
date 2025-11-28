package br.com.fiap.chatbotdatabase1.controller;

import br.com.fiap.chatbotdatabase1.dto.MensagemDTO;
import br.com.fiap.chatbotdatabase1.model.Mensagem;
import br.com.fiap.chatbotdatabase1.repository.MensagemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    private final MensagemRepository mensagemRepository;

    public MensagemController(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    @GetMapping
    public ResponseEntity<List<Mensagem>> listarMensagens() {
        return ResponseEntity.ok(mensagemRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Mensagem> criarMensagem(@RequestBody MensagemDTO dto) {
        Mensagem mensagem = new Mensagem(dto.getTexto(), dto.getUsuario());
        return ResponseEntity.ok(mensagemRepository.save(mensagem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> buscarPorId(@PathVariable Long id) {
        return mensagemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mensagem> atualizarMensagem(
            @PathVariable Long id,
            @RequestBody MensagemDTO dto) {

        return mensagemRepository.findById(id).map(m -> {
            m.setTexto(dto.getTexto());
            m.setUsuario(dto.getUsuario());
            return ResponseEntity.ok(mensagemRepository.save(m));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!mensagemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        mensagemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
