package br.com.fiap.chatbotdatabase1.dto;

import br.com.fiap.chatbotdatabase1.model.Mensagem;
import br.com.fiap.chatbotdatabase1.model.Resposta;

public record ListagemMensagemDTO(
        long id,
        String conteudo,
        Resposta resposta) {
    public ListagemMensagemDTO(Mensagem mensagem) {
        this(mensagem.getId(), mensagem.getConteudo(), mensagem.getResposta());
    }
}
