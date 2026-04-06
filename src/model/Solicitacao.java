package model;

public class Solicitacao {
    private int id;
    private Beneficiario beneficiario;
    private ItemDoacao item;
    private int quantidadeSolicitada;
    private String justificativa;
    private String status;

    public Solicitacao(int id, Beneficiario beneficiario, ItemDoacao item, int quantidadeSolicitada, String justificativa, String status){
        this.id = id;
        this.beneficiario = beneficiario;
        this.item = item;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.justificativa = justificativa;
        this.status = status;
    }
    public int getId() {return id; }
    public void setId(int id) {this.id = id; }
    public Beneficiario getBeneficiario() {return beneficiario; }
    public void setBeneficiario(Beneficiario beneficiario) {this.beneficiario = beneficiario; }
    public ItemDoacao getItem() {return item; }
    public void setItem(ItemDoacao item) {this.item = item; }
    public int getQuantidadesolicitada() {return quantidadeSolicitada; }
    public void setQuantidadeSolicitada(int quantidadeSolicitada) {this.quantidadeSolicitada = quantidadeSolicitada; }
    public String getJustificativa() {return justificativa; }
    public void setJustificativa(String justificativa) {this.justificativa = justificativa; }
    public String getStatus() {return status; }
    public void setStatus(String status) {this.status = status; }
    
}
