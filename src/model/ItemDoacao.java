package model;

public class ItemDoacao {
    private int id;
    private String nome;
    private String categoria;
    private String descricao;
    private int quantidade;
    private String estadoConservacao;
    private String dataCadastro;
    private String status;

    public ItemDoacao(int id, String nome, String categoria, String descricao, int quantidade, String estadoConservacao, String dataCadastro, String status){
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.estadoConservacao = estadoConservacao;
        this.dataCadastro = dataCadastro;
        this.status = status;
    }
    public int getId() {return id; }
    public void setId(int id) {this.id = id; }
    public String getNome() {return nome; }
    public void setNome(String nome) {this.nome = nome; }
    public String getCategoria() {return categoria; }
    public void setCategoria(String categoria) {this.categoria = categoria; }
    public String getDescricao() {return descricao; }
    public void setDescricao(String descricao) {this.descricao = descricao; }
    public int getQuantidade() {return quantidade; }
    public void setQuantidade(int quantidade) {this.quantidade = quantidade; }
    public String getEstadoConservacao() {return estadoConservacao; }
    public void setEstadoConservacao(String estadoConservacao) {this.estadoConservacao = estadoConservacao; }
    public String getDataCadastro() {return dataCadastro; }
    public void setDataCadastro(String dataCadastro) {this.dataCadastro = dataCadastro;}
    public String getStatus() {return status; }
    public void setStatus(String status) {this.status = status; }
 

    
}
