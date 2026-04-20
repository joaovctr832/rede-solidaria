package model;

public class Solicitacao implements Identificavel {
    private Long id;
    private Beneficiario beneficiario;
    private ItemDoacao itemDoacao;
    private int quantidadeSolicitada;
    private String justificativa;
    private StatusSolicitacao status;

    public Solicitacao(
            Beneficiario beneficiario,
            ItemDoacao itemDoacao,
            int quantidadeSolicitada,
            String justificativa) {
        this.setBeneficiario(beneficiario);
        this.setItemDoacao(itemDoacao);
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
        if (this.beneficiario == beneficiario) {
            return;
        }

        Beneficiario beneficiarioAnterior = this.beneficiario;
        this.beneficiario = beneficiario;

        if (beneficiarioAnterior != null) {
            beneficiarioAnterior.removerSolicitacao(this);
        }

        if (beneficiario != null && !beneficiario.getSolicitacoes().contains(this)) {
            beneficiario.adicionarSolicitacao(this);
        }
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
            itemDoacaoAnterior.removerSolicitacao(this);
        }

        if (itemDoacao != null && !itemDoacao.getSolicitacoes().contains(this)) {
            itemDoacao.adicionarSolicitacao(this);
        }
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
                beneficiario != null ? beneficiario.getNome() : "nao informado",
                itemDoacao != null ? itemDoacao.getNome() : "nao informado",
                quantidadeSolicitada,
                status);
    }
}
