package model;

public class Beneficiario extends Usuario {
    private String tipo;
    private int nivelPrioridade;

    public Beneficiario(int id, String nome, String telefone, String email, String endereco, String tipo, int nivelPrioridade){
        super(id, nome, telefone, email, endereco);
        this.tipo = tipo;
        this.nivelPrioridade = nivelPrioridade;
        
    }
    public String getTipo() {return tipo; }
    public void setTipo(String tipo) {this.tipo = tipo; }
    public int getNivelPrioridade() {return nivelPrioridade; }
    public void setNivelPrioridade(int nivelPrioridade) {this.nivelPrioridade = nivelPrioridade; }
    
}
