package br.com.fiap.chatbotdatabase1.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.fiap.chatbotdatabase1.dto.AtualizarRespostaDTO;
import br.com.fiap.chatbotdatabase1.dto.RespostaDTO;
import br.com.fiap.chatbotdatabase1.enums.Tema;
import br.com.fiap.chatbotdatabase1.util.SanitizerUtil;
import jakarta.persistence.Column;
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
import lombok.Setter;

@Table(name = "RESPOSTA")
@Entity(name = "Resposta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESPOSTA")
    private Long id;

    @Column(name = "CONTEUDO", length = 500, nullable = false)
    private String conteudo;

    @Enumerated(EnumType.STRING)
    @Column(name = "TEMA_PRINCIPAL", length = 50, nullable = false)
    private Tema temaPrincipal;

    @Column(name = "SATISFATORIO")
    private Boolean satisfatorio;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

    @OneToOne
    @JoinColumn(name = "ID_MENSAGEM", nullable = false)
    @JsonBackReference
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