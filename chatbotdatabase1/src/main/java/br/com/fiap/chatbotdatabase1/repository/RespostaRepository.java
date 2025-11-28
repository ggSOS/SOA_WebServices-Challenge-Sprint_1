package br.com.fiap.chatbotdatabase1.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fiap.chatbotdatabase1.model.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long>{
    Page<Resposta> findAllByAtivoTrue(Pageable paginacao);

    @Query("select r from Resposta r where r.id = :id and r.ativo = true")
    Optional<Resposta> findAtivoById(Long id);
}
