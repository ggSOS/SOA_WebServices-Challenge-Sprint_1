package br.com.fiap.chatbotdatabase1.dto;

import br.com.fiap.chatbotdatabase1.model.Mensagem;
import jakarta.validation.constraints.NotNull;

public record AtualizarRespostaDTO(
        @NotNull
        long id,

        Boolean satisfatorio,
        Mensagem mensagem) {

}
