package br.com.fiap.chatbotdatabase1.dto;

import br.com.fiap.chatbotdatabase1.enums.Tema;
import br.com.fiap.chatbotdatabase1.model.Mensagem;
import br.com.fiap.chatbotdatabase1.model.Resposta;

public record ListagemMensagemDTO(
        long id,
        String conteudo,
        Boolean ativo,
        RespostaResumo resposta) {

    public ListagemMensagemDTO(Mensagem mensagem) {
        this(
                mensagem.getId(),
                mensagem.getConteudo(),
                mensagem.getAtivo(),
                mensagem.getResposta() != null ? new RespostaResumo(mensagem.getResposta()) : null
        );
    }

    public record RespostaResumo(
            long id,
            String conteudo,
            Tema temaPrincipal,
            Boolean satisfatorio
    ) {
        public RespostaResumo(Resposta resposta) {
            this(
                    resposta.getId(),
                    resposta.getConteudo(),
                    resposta.getTemaPrincipal(),
                    resposta.getSatisfatorio()
            );
        }
    }
}
