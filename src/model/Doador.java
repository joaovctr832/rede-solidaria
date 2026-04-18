package model;

public class Doador extends Usuario {
    public Doador(String nome, String telefone, String email, String endereco) {
        super(nome, telefone, email, endereco);
    }

    @Override
    public String toString() {
        return "Doador " + resumoBasico();
    }
}

