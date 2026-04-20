package model;

import java.util.ArrayList;
import java.util.List;

public class Doador extends Usuario {
    private final List<ItemDoacao> itensDoacao;
    private final List<DoacaoEfetivada> doacoesEfetivadas;
    private int quantidadeItensDoacao;
    private int quantidadeDoacoesEfetivadas;

    public Doador(String nome, String telefone, String email, String endereco) {
        super(nome, telefone, email, endereco);
        this.itensDoacao = new ArrayList<>();
        this.doacoesEfetivadas = new ArrayList<>();
    }

    public List<ItemDoacao> getItensDoacao() {
        return List.copyOf(itensDoacao);
    }

    public int getQuantidadeItensDoacao() {
        return itensDoacao.isEmpty() ? quantidadeItensDoacao : itensDoacao.size();
    }

    public void setQuantidadeItensDoacao(int quantidadeItensDoacao) {
        this.quantidadeItensDoacao = quantidadeItensDoacao;
    }

    public void adicionarItemDoacao(ItemDoacao itemDoacao) {
        validarItemDoacao(itemDoacao);
        if (itensDoacao.contains(itemDoacao)) {
            return;
        }

        itensDoacao.add(itemDoacao);
        this.quantidadeItensDoacao = itensDoacao.size();
        if (itemDoacao.getDoador() != this) {
            itemDoacao.setDoador(this);
        }
    }

    public void removerItemDoacao(ItemDoacao itemDoacao) {
        if (itemDoacao == null) {
            return;
        }

        if (itensDoacao.remove(itemDoacao) && itemDoacao.getDoador() == this) {
            this.quantidadeItensDoacao = itensDoacao.size();
            itemDoacao.setDoador(null);
        }
    }

    public List<DoacaoEfetivada> getDoacoesEfetivadas() {
        return List.copyOf(doacoesEfetivadas);
    }

    public int getQuantidadeDoacoesEfetivadas() {
        return doacoesEfetivadas.isEmpty() ? quantidadeDoacoesEfetivadas : doacoesEfetivadas.size();
    }

    public void setQuantidadeDoacoesEfetivadas(int quantidadeDoacoesEfetivadas) {
        this.quantidadeDoacoesEfetivadas = quantidadeDoacoesEfetivadas;
    }

    public void adicionarDoacaoEfetivada(DoacaoEfetivada doacaoEfetivada) {
        validarDoacaoEfetivada(doacaoEfetivada);
        if (doacoesEfetivadas.contains(doacaoEfetivada)) {
            return;
        }

        doacoesEfetivadas.add(doacaoEfetivada);
        this.quantidadeDoacoesEfetivadas = doacoesEfetivadas.size();
        if (doacaoEfetivada.getDoador() != this) {
            doacaoEfetivada.setDoador(this);
        }
    }

    public void removerDoacaoEfetivada(DoacaoEfetivada doacaoEfetivada) {
        if (doacaoEfetivada == null) {
            return;
        }

        if (doacoesEfetivadas.remove(doacaoEfetivada) && doacaoEfetivada.getDoador() == this) {
            this.quantidadeDoacoesEfetivadas = doacoesEfetivadas.size();
            doacaoEfetivada.setDoador(null);
        }
    }

    private void validarItemDoacao(ItemDoacao itemDoacao) {
        if (itemDoacao == null) {
            throw new IllegalArgumentException("O item de doacao informado nao pode ser nulo.");
        }
    }

    private void validarDoacaoEfetivada(DoacaoEfetivada doacaoEfetivada) {
        if (doacaoEfetivada == null) {
            throw new IllegalArgumentException("A doacao efetivada informada nao pode ser nula.");
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Doador %s | itens vinculados: %d | doacoes efetivadas: %d",
                resumoBasico(),
                getQuantidadeItensDoacao(),
                getQuantidadeDoacoesEfetivadas());
    }
}
