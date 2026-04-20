package model;

import java.time.LocalDate;

public class DoacaoEfetivada implements Identificavel {
    private Long id;
    private ItemDoacao itemDoacao;
    private Doador doador;
    private Beneficiario beneficiario;
    private LocalDate data;
    private String observacoes;

    public DoacaoEfetivada(
            ItemDoacao itemDoacao,
            Doador doador,
            Beneficiario beneficiario,
            LocalDate data,
            String observacoes) {
        this.setItemDoacao(itemDoacao);
        this.setDoador(doador);
        this.setBeneficiario(beneficiario);
        this.data = data;
        this.observacoes = observacoes;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ItemDoacao getItemDoacao() {
        return itemDoacao;
    }

    public void setItemDoacao(ItemDoacao itemDoacao) {
        if (this.itemDoacao == itemDoacao) {
            return;
        }

        ItemDoacao itemDoacaoAnterior = this.itemDoacao;
        this.itemDoacao = itemDoacao;

        if (itemDoacaoAnterior != null) {
            itemDoacaoAnterior.removerDoacaoEfetivada(this);
        }

        if (itemDoacao != null && !itemDoacao.getDoacoesEfetivadas().contains(this)) {
            itemDoacao.adicionarDoacaoEfetivada(this);
        }
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
            doadorAnterior.removerDoacaoEfetivada(this);
        }

        if (doador != null && !doador.getDoacoesEfetivadas().contains(this)) {
            doador.adicionarDoacaoEfetivada(this);
        }
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        if (this.beneficiario == beneficiario) {
            return;
        }

        Beneficiario beneficiarioAnterior = this.beneficiario;
        this.beneficiario = beneficiario;

        if (beneficiarioAnterior != null) {
            beneficiarioAnterior.removerDoacaoEfetivada(this);
        }

        if (beneficiario != null && !beneficiario.getDoacoesEfetivadas().contains(this)) {
            beneficiario.adicionarDoacaoEfetivada(this);
        }
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return String.format(
                "#%d - item: %s | doador: %s | beneficiario: %s | data: %s",
                id,
                itemDoacao != null ? itemDoacao.getNome() : "nao informado",
                doador != null ? doador.getNome() : "nao informado",
                beneficiario != null ? beneficiario.getNome() : "nao informado",
                data);
    }
}
