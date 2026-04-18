package model;

public class Beneficiario extends Usuario {
    private TipoBeneficiario tipo;
    private int nivelPrioridade;

    public Beneficiario(
            String nome,
            String telefone,
            String email,
            String endereco,
            TipoBeneficiario tipo,
            int nivelPrioridade) {
        super(nome, telefone, email, endereco);
        this.tipo = tipo;
        this.nivelPrioridade = nivelPrioridade;
    }

    public TipoBeneficiario getTipo() {
        return tipo;
    }

    public void setTipo(TipoBeneficiario tipo) {
        this.tipo = tipo;
    }

    public int getNivelPrioridade() {
        return nivelPrioridade;
    }

    public void setNivelPrioridade(int nivelPrioridade) {
        this.nivelPrioridade = nivelPrioridade;
    }

    @Override
    public String toString() {
        return String.format(
                "Beneficiario %s | tipo: %s | prioridade: %d",
                resumoBasico(),
                tipo,
                nivelPrioridade);
    }
}

