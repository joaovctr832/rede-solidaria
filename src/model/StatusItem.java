package model;

public enum StatusItem {
    DISPONIVEL("Disponivel"),
    RESERVADO("Reservado"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    private StatusItem(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
