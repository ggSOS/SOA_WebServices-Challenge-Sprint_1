package br.com.fiap.chatbotdatabase1.dto;

import br.com.fiap.chatbotdatabase1.model.Resposta;
import jakarta.validation.constraints.NotNull;

public record AtualizarMensagemDTO(
        @NotNull
        long id,

        Resposta resposta) {

}
