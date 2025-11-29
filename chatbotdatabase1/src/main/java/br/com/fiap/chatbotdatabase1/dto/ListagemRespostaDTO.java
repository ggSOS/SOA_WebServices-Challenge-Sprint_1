package br.com.fiap.chatbotdatabase1.dto;

import br.com.fiap.chatbotdatabase1.enums.Tema;
import br.com.fiap.chatbotdatabase1.model.Resposta;

public record ListagemRespostaDTO(
        long id,
        String conteudo,
        Tema temaPrincipal,
        Boolean satisfatorio,
        Long mensagemId,
        String mensagemConteudo) {
    public ListagemRespostaDTO(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getConteudo(),
                resposta.getTemaPrincipal(),
                resposta.getSatisfatorio(),
                resposta.getMensagem() != null ? resposta.getMensagem().getId() : null,
                resposta.getMensagem() != null ? resposta.getMensagem().getConteudo() : null
        );
    }
}
