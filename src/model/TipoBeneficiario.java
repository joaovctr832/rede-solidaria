package model;

public enum TipoBeneficiario {
    FAMILIA("Familia"),
    ONG("ONG"),
    ESCOLA("Escola"),
    ABRIGO("Abrigo"),
    COMUNIDADE("Comunidade");

    private final String descricao;

    TipoBeneficiario(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

