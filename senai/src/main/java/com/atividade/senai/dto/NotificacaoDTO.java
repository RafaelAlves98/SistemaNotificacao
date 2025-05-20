package com.atividade.senai.dto;

import lombok.Data;

@Data
public class NotificacaoDTO {
    private Integer id;
    private String usuarioCpf;
    private Integer produtoId;
    private String mensagem;
    private String categoria;
    private String status;
}