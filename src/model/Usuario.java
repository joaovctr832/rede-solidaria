package model;

public abstract class Usuario implements Identificavel {
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String endereco;

    protected Usuario(String nome, String telefone, String email, String endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    protected String resumoBasico() {
        return String.format(
                "#%d - %s | telefone: %s | email: %s | endereco: %s",
                id,
                nome,
                telefone,
                email,
                endereco);
    }
}

