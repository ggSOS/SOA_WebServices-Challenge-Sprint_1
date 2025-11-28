package br.com.fiap.chatbotdatabase1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chatbotdatabase1.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    Page<Mensagem> findAllByAtivoTrue(Pageable paginacao);
}
