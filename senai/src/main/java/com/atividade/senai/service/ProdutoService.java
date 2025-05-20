package com.atividade.senai.service;

import com.atividade.senai.dto.ProdutoDTO;
import com.atividade.senai.entity.Notificacao;
import com.atividade.senai.entity.Produto;
import com.atividade.senai.entity.Usuario;
import com.atividade.senai.repository.ProdutoRepository;
import com.atividade.senai.repository.NotificacaoRepository;
import com.atividade.senai.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Produto criarProduto(ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());
        return produtoRepository.save(produto);
    }

    public Produto obterProduto(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    public Produto atualizarProduto(Integer id, ProdutoDTO dto) {
        Produto produto = obterProduto(id);
        BigDecimal precoAntigo = produto.getPreco();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());
        produto.setCategoria(dto.getCategoria());

        if (!precoAntigo.equals(dto.getPreco())) {
            List<Usuario> usuarios = usuarioRepository.findAll();
            for (Usuario usuario : usuarios) {
                Notificacao notificacao = new Notificacao();
                notificacao.setUsuario(usuario);
                notificacao.setProduto(produto);
                notificacao.setMensagem(String.format("Preço de %s alterado de %s para %s",
                        produto.getNome(), precoAntigo, produto.getPreco()));
                notificacao.setCategoria("Atualização de Preço");
                notificacao.setStatus("pendente");
                notificacaoRepository.save(notificacao);
            }
        }

        return produtoRepository.save(produto);
    }

    public void deletarProduto(Integer id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
}