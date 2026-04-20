package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private final List<Solicitacao> solicitacoes;
    private final List<DoacaoEfetivada> doacoesEfetivadas;

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
        this.solicitacoes = new ArrayList<>();
        this.doacoesEfetivadas = new ArrayList<>();
        this.setDoador(doador);
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

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
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
        if (this.doador == doador) {
            return;
        }

        Doador doadorAnterior = this.doador;
        this.doador = doador;

        if (doadorAnterior != null) {
            doadorAnterior.removerItemDoacao(this);
        }

        if (doador != null && !doador.getItensDoacao().contains(this)) {
            doador.adicionarItemDoacao(this);
        }
    }

    public List<Solicitacao> getSolicitacoes() {
        return List.copyOf(solicitacoes);
    }

    public void adicionarSolicitacao(Solicitacao solicitacao) {
        validarSolicitacao(solicitacao);
        if (solicitacoes.contains(solicitacao)) {
            return;
        }

        solicitacoes.add(solicitacao);
        if (solicitacao.getItemDoacao() != this) {
            solicitacao.setItemDoacao(this);
        }
    }

    public void removerSolicitacao(Solicitacao solicitacao) {
        if (solicitacao == null) {
            return;
        }

        if (solicitacoes.remove(solicitacao) && solicitacao.getItemDoacao() == this) {
            solicitacao.setItemDoacao(null);
        }
    }

    public List<DoacaoEfetivada> getDoacoesEfetivadas() {
        return List.copyOf(doacoesEfetivadas);
    }

    public void adicionarDoacaoEfetivada(DoacaoEfetivada doacaoEfetivada) {
        validarDoacaoEfetivada(doacaoEfetivada);
        if (doacoesEfetivadas.contains(doacaoEfetivada)) {
            return;
        }

        doacoesEfetivadas.add(doacaoEfetivada);
        if (doacaoEfetivada.getItemDoacao() != this) {
            doacaoEfetivada.setItemDoacao(this);
        }
    }

    public void removerDoacaoEfetivada(DoacaoEfetivada doacaoEfetivada) {
        if (doacaoEfetivada == null) {
            return;
        }

        if (doacoesEfetivadas.remove(doacaoEfetivada) && doacaoEfetivada.getItemDoacao() == this) {
            doacaoEfetivada.setItemDoacao(null);
        }
    }

    private void validarSolicitacao(Solicitacao solicitacao) {
        if (solicitacao == null) {
            throw new IllegalArgumentException("A solicitacao informada nao pode ser nula.");
        }
    }

    private void validarDoacaoEfetivada(DoacaoEfetivada doacaoEfetivada) {
        if (doacaoEfetivada == null) {
            throw new IllegalArgumentException("A doacao efetivada informada nao pode ser nula.");
        }
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
