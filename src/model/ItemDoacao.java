package model;

import java.time.LocalDate;

public class ItemDoacao implements Identificavel {
    private Long id;
    private String nome;
    private CategoriaItem categoria;
    private String descricao;
    private int quantidade;
    private EstadoConservacao estadoConservacao;
    private LocalDate dataCadastro;
    private StatusItem status;
    private Doador doador;

    public ItemDoacao(
            String nome,
            CategoriaItem categoria,
            String descricao,
            int quantidade,
            EstadoConservacao estadoConservacao,
            Doador doador) {
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.estadoConservacao = estadoConservacao;
        this.dataCadastro = LocalDate.now();
        this.status = StatusItem.DISPONIVEL;
        this.doador = doador;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaItem categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public EstadoConservacao getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(EstadoConservacao estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public StatusItem getStatus() {
        return status;
    }

    public void setStatus(StatusItem status) {
        this.status = status;
    }

    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }

    @Override
    public String toString() {
        return String.format(
                "#%d - %s | categoria: %s | qtd: %d | estado: %s | status: %s | doador: %s",
                id,
                nome,
                categoria,
                quantidade,
                estadoConservacao,
                status,
                doador != null ? doador.getNome() : "nao informado");
    }
}

