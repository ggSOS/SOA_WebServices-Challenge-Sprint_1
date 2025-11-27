package br.com.fiap.chatbotdatabase1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.chatbotdatabase1.model.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long>{
    
}
