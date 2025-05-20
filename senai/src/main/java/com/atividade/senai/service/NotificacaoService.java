package com.atividade.senai.service;

import com.atividade.senai.dto.NotificacaoDTO;
import com.atividade.senai.entity.Notificacao;
import com.atividade.senai.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacaoService {
    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public List<NotificacaoDTO> listarNotificacoes(String usuarioCpf, String status) {
        List<Notificacao> notificacoes = (status != null && (status.equals("pendente") || status.equals("lida")))
                ? notificacaoRepository.findByUsuarioCpfAndStatus(usuarioCpf, status)
                : notificacaoRepository.findByUsuarioCpf(usuarioCpf);

        return notificacoes.stream().map(n -> {
            NotificacaoDTO dto = new NotificacaoDTO();
            dto.setId(n.getId());
            dto.setUsuarioCpf(n.getUsuario().getCpf());
            dto.setProdutoId(n.getProduto().getId());
            dto.setMensagem(n.getMensagem());
            dto.setCategoria(n.getCategoria());
            dto.setStatus(n.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }

    public void atualizarStatusNotificacao(Integer id, String usuarioCpf, String status) {
        if (!status.equals("pendente") && !status.equals("lida")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status inválido");
        }
        Notificacao notificacao = notificacaoRepository.findById(id)
                .filter(n -> n.getUsuario().getCpf().equals(usuarioCpf))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificação não encontrada"));
        notificacao.setStatus(status);
        notificacaoRepository.save(notificacao);
    }
}