package model;

public enum EstadoConservacao {
    NOVO("Novo"),
    BOM("Bom"),
    USADO("Usado"),
    PRECISA_REPARO("Precisa de reparo");

    private final String descricao;

    EstadoConservacao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

