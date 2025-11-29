package br.com.fiap.chatbotdatabase1.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.chatbotdatabase1.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    Page<Mensagem> findAllByAtivoTrue(Pageable paginacao);

    @Query("select m from Mensagem m where m.id = :id and m.ativo = true")
    Optional<Mensagem> findAtivoById(Long id);
}
