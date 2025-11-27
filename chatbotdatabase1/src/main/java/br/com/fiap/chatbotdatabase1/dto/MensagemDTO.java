package br.com.fiap.chatbotdatabase1.dto;

import br.com.fiap.chatbotdatabase1.model.Resposta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MensagemDTO(
        @Size(max = 500, message = "Erro: Conteúdo da Mensagem deve ter no máximo 500 caracteres")
        @NotBlank(message = "Erro: Conteúdo da Mensagem(Not Empty)")
        String conteudo,

        Resposta resposta) {
}
