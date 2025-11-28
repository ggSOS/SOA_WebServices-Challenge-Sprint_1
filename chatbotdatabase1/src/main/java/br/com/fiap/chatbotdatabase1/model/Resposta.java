package br.com.fiap.chatbotdatabase1.model;

import br.com.fiap.chatbotdatabase1.dto.AtualizarRespostaDTO;
import br.com.fiap.chatbotdatabase1.dto.RespostaDTO;
import br.com.fiap.chatbotdatabase1.enums.Tema;
import br.com.fiap.chatbotdatabase1.util.SanitizerUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "RESPOSTA")
@Entity(name = "Resposta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String conteudo;

    @Enumerated(EnumType.STRING)
    private Tema temaPrincipal;

    private Boolean satisfatorio;

    private Boolean ativo;

    @OneToOne
    @JoinColumn(name = "ID_MENSAGEM")
    private Mensagem mensagem;

    public Resposta(RespostaDTO dados) {
        this.conteudo = SanitizerUtil.sanitize(dados.conteudo());
        this.temaPrincipal = dados.temaPrincipal();
        this.satisfatorio = dados.satisfatorio();
        this.mensagem = dados.mensagem();
        this.ativo = true;
    }

    public void atualizarSatisfacao(@Valid AtualizarRespostaDTO dados) {
        if (dados.satisfatorio() != null) {
            this.satisfatorio = dados.satisfatorio();
        }
        if (dados.mensagem() != null) {
            this.mensagem = dados.mensagem();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
