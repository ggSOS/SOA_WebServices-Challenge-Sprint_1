package br.com.fiap.chatbotdatabase1.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.fiap.chatbotdatabase1.dto.AtualizarMensagemDTO;
import br.com.fiap.chatbotdatabase1.dto.MensagemDTO;
import br.com.fiap.chatbotdatabase1.util.SanitizerUtil;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import lombok.Setter;

@Table(name = "MENSAGEM")
@Entity(name = "Mensagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MENSAGEM")
    private Long id;

    @Column(name = "CONTEUDO", length = 500, nullable = false)
    private String conteudo;

    @Column(name = "ATIVO", nullable = false)
    private Boolean ativo;

    @OneToOne(mappedBy = "mensagem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Resposta resposta;

    public Mensagem(MensagemDTO dados) {
        this.conteudo = SanitizerUtil.sanitize(dados.conteudo());
        this.ativo = true;
    }

    public void atualizarMensagem(@Valid AtualizarMensagemDTO dados){
        if(dados.resposta() != null){
            this.resposta = dados.resposta();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}