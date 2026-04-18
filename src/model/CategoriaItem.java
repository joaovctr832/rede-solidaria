package model;

public enum CategoriaItem {
    ALIMENTO_NAO_PERECIVEL("Alimento nao perecivel"),
    ROUPA("Roupa"),
    MATERIAL_ESCOLAR("Material escolar"),
    MOVEL("Movel"),
    HIGIENE("Higiene"),
    OUTROS("Outros");

    private final String descricao;

    CategoriaItem(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}

