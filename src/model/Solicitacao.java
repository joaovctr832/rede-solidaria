package model;

public class Solicitacao implements Identificavel {
    private Long id;
    private Beneficiario beneficiario;
    private ItemDoacao item;
    private int quantidadeSolicitada;
    private String justificativa;
    private StatusSolicitacao status;

    public Solicitacao(
            Beneficiario beneficiario,
            ItemDoacao item,
            int quantidadeSolicitada,
            String justificativa) {
        this.beneficiario = beneficiario;
        this.item = item;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.justificativa = justificativa;
        this.status = StatusSolicitacao.PENDENTE;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Beneficiario getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Beneficiario beneficiario) {
        this.beneficiario = beneficiario;
    }

    public ItemDoacao getItem() {
        return item;
    }

    public void setItem(ItemDoacao item) {
        this.item = item;
    }

    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }

    public void setQuantidadeSolicitada(int quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "#%d - beneficiario: %s | item: %s | qtd: %d | status: %s",
                id,
                beneficiario.getNome(),
                item.getNome(),
                quantidadeSolicitada,
                status);
    }
}

