package br.com.fiap.chatbotdatabase1.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.chatbotdatabase1.model.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long>{
    
    @Query("SELECT r FROM Resposta r WHERE r.ativo = true")
    Page<Resposta> findAllByAtivoTrue(Pageable paginacao);

    @Query("SELECT r FROM Resposta r WHERE r.id = :id AND r.ativo = true")
    Optional<Resposta> findAtivoById(@Param("id") Long id);
}