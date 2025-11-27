package br.com.fiap.chatbotdatabase1.model;

import br.com.fiap.chatbotdatabase1.dto.AtualizarMensagemDTO;
import br.com.fiap.chatbotdatabase1.dto.MensagemDTO;
import br.com.fiap.chatbotdatabase1.util.SanitizerUtil;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "MENSAGEM")
@Entity(name = "Mensagem")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String conteudo;

    @OneToOne(mappedBy = "mensagem", cascade = CascadeType.ALL)
    private Resposta resposta;

    public Mensagem(MensagemDTO dados) {
        this.conteudo = SanitizerUtil.sanitize(dados.conteudo());
        this.resposta = dados.resposta();
    }

    public void atualizarMensagem(@Valid AtualizarMensagemDTO dados){
        if(dados.resposta() != null){
            this.resposta = dados.resposta();
        }
    }
}
