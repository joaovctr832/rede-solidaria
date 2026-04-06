package model;

public class DoacaoEfetivada {
    private int id;
    private ItemDoacao item;
    private Doador doador;
    private Beneficiario beneficiario;
    private String data;
    private String observacoes;

    public DoacaoEfetivada(int id, ItemDoacao item, Doador doador, Beneficiario beneficiario, String data, String observacoes){
        this.id = id;
        this.item = item;
        this.doador = doador;
        this.beneficiario = beneficiario;
        this.data = data;
        this.observacoes = observacoes;
    }
    public int getId() {return id; }
    public void setId(int id) {this.id = id; }
    public ItemDoacao getItem() {return item; }
    public void setItem(ItemDoacao item) {this.item = item; }
    public Doador getDoador() {return doador; }
    public void setDoador(Doador doador) {this.doador = doador; }
    public Beneficiario getBeneficiario() {return beneficiario; }
    public void setBeneficiario(Beneficiario beneficiario) {this.beneficiario = beneficiario; }
    public String getData() {return data; }
    public void setData(String data) {this.data = data; }
    public String getObservacoes() {return observacoes; }
    public void setObservacoes(String observacoes) {this.observacoes = observacoes; }

    
}
