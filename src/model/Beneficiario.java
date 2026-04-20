package model;

import java.util.ArrayList;
import java.util.List;

public class Beneficiario extends Usuario {
    private TipoBeneficiario tipo;
    private int nivelPrioridade;
    private final List<Solicitacao> solicitacoes;
    private final List<DoacaoEfetivada> doacoesEfetivadas;
    private int quantidadeSolicitacoes;
    private int quantidadeDoacoesEfetivadas;

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
        this.solicitacoes = new ArrayList<>();
        this.doacoesEfetivadas = new ArrayList<>();
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

    public List<Solicitacao> getSolicitacoes() {
        return List.copyOf(solicitacoes);
    }

    public int getQuantidadeSolicitacoes() {
        return solicitacoes.isEmpty() ? quantidadeSolicitacoes : solicitacoes.size();
    }

    public void setQuantidadeSolicitacoes(int quantidadeSolicitacoes) {
        this.quantidadeSolicitacoes = quantidadeSolicitacoes;
    }

    public void adicionarSolicitacao(Solicitacao solicitacao) {
        validarSolicitacao(solicitacao);
        if (solicitacoes.contains(solicitacao)) {
            return;
        }

        solicitacoes.add(solicitacao);
        this.quantidadeSolicitacoes = solicitacoes.size();
        if (solicitacao.getBeneficiario() != this) {
            solicitacao.setBeneficiario(this);
        }
    }

    public void removerSolicitacao(Solicitacao solicitacao) {
        if (solicitacao == null) {
            return;
        }

        if (solicitacoes.remove(solicitacao) && solicitacao.getBeneficiario() == this) {
            this.quantidadeSolicitacoes = solicitacoes.size();
            solicitacao.setBeneficiario(null);
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
        if (doacaoEfetivada.getBeneficiario() != this) {
            doacaoEfetivada.setBeneficiario(this);
        }
    }

    public void removerDoacaoEfetivada(DoacaoEfetivada doacaoEfetivada) {
        if (doacaoEfetivada == null) {
            return;
        }

        if (doacoesEfetivadas.remove(doacaoEfetivada) && doacaoEfetivada.getBeneficiario() == this) {
            this.quantidadeDoacoesEfetivadas = doacoesEfetivadas.size();
            doacaoEfetivada.setBeneficiario(null);
        }
    }

    private void validarSolicitacao(Solicitacao solicitacao) {
        if (solicitacao == null) {
            throw new IllegalArgumentException("A solicitacao informada nao pode ser nula.");
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
                "Beneficiario %s | tipo: %s | prioridade: %d | solicitacoes: %d | doacoes efetivadas: %d",
                resumoBasico(),
                tipo,
                nivelPrioridade,
                getQuantidadeSolicitacoes(),
                getQuantidadeDoacoesEfetivadas());
    }
}
