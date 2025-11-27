package br.com.fiap.chatbotdatabase1.dto;

import br.com.fiap.chatbotdatabase1.enums.Tema;
import br.com.fiap.chatbotdatabase1.model.Mensagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RespostaDTO(
        @Size(max = 500, message = "Erro: Conteúdo da Mensagem deve ter no máximo 500 caracteres")
        @NotBlank(message = "Erro: Conteúdo da Resposta(Not Blank)")
        String conteudo,

        @NotNull(message = "Erro: Tema da Resposta(Not Null)")
        Tema temaPrincipal,

        Boolean satisfatorio,
        Mensagem mensagem) {

}
