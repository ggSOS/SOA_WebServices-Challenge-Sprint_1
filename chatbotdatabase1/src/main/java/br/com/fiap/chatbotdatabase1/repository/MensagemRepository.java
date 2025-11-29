package br.com.fiap.chatbotdatabase1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.chatbotdatabase1.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    
    @Query("SELECT m FROM Mensagem m WHERE m.ativo = true")
    Page<Mensagem> findAllByAtivoTrue(Pageable paginacao);
}