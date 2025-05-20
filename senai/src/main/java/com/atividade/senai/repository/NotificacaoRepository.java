package com.atividade.senai.repository;

import com.atividade.senai.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {
    List<Notificacao> findByUsuarioCpfAndStatus(String usuarioCpf, String status);
    List<Notificacao> findByUsuarioCpf(String usuarioCpf);
}