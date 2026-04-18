package model;

import java.time.LocalDate;

public class DoacaoEfetivada implements Identificavel {
    private Long id;
    private ItemDoacao item;
    private Doador doador;
    private Beneficiario beneficiario;
    private LocalDate data;
    private String observacoes;

    public DoacaoEfetivada(
            ItemDoacao item,
            Doador doador,
            Beneficiario beneficiario,
            LocalDate data,
            String observacoes) {
        this.item = item;
        this.doador = doador;
        this.beneficiario = beneficiario;
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

    public ItemDoacao getItem() {
        return item;
    }

    public void setItem(ItemDoacao item) {
        this.item = item;
    }

    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
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
                item.getNome(),
                doador.getNome(),
                beneficiario.getNome(),
                data);
    }
}
