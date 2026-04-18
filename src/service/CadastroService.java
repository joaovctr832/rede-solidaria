package service;

import java.util.Comparator;
import java.util.List;
import model.Beneficiario;
import model.CategoriaItem;
import model.Doador;
import model.EstadoConservacao;
import model.ItemDoacao;
import model.StatusItem;
import model.TipoBeneficiario;
import repository.BeneficiarioRepository;
import repository.DoadorRepository;
import repository.ItemDoacaoRepository;

public class CadastroService {
    private final DoadorRepository doadorRepository;
    private final BeneficiarioRepository beneficiarioRepository;
    private final ItemDoacaoRepository itemDoacaoRepository;

    public CadastroService(
            DoadorRepository doadorRepository,
            BeneficiarioRepository beneficiarioRepository,
            ItemDoacaoRepository itemDoacaoRepository) {
        this.doadorRepository = doadorRepository;
        this.beneficiarioRepository = beneficiarioRepository;
        this.itemDoacaoRepository = itemDoacaoRepository;
    }

    public Doador cadastrarDoador(String nome, String telefone, String email, String endereco) {
        validarTextoObrigatorio(nome, "Nome do doador");
        validarTextoObrigatorio(telefone, "Telefone do doador");
        validarTextoObrigatorio(email, "Email do doador");
        validarTextoObrigatorio(endereco, "Endereco do doador");

        Doador doador = new Doador(nome.trim(), telefone.trim(), email.trim(), endereco.trim());
        return doadorRepository.save(doador);
    }

    public Beneficiario cadastrarBeneficiario(
            String nome,
            String telefone,
            String email,
            String endereco,
            TipoBeneficiario tipo,
            int nivelPrioridade) {
        validarTextoObrigatorio(nome, "Nome do beneficiario");
        validarTextoObrigatorio(telefone, "Telefone do beneficiario");
        validarTextoObrigatorio(email, "Email do beneficiario");
        validarTextoObrigatorio(endereco, "Endereco do beneficiario");

        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de beneficiario e obrigatorio.");
        }

        if (nivelPrioridade < 1 || nivelPrioridade > 5) {
            throw new IllegalArgumentException("A prioridade deve estar entre 1 e 5.");
        }

        Beneficiario beneficiario = new Beneficiario(
                nome.trim(),
                telefone.trim(),
                email.trim(),
                endereco.trim(),
                tipo,
                nivelPrioridade);
        return beneficiarioRepository.save(beneficiario);
    }

    public ItemDoacao cadastrarItem(
            Long doadorId,
            String nome,
            CategoriaItem categoria,
            String descricao,
            int quantidade,
            EstadoConservacao estadoConservacao) {
        if (doadorId == null) {
            throw new IllegalArgumentException("O item precisa estar associado a um doador.");
        }

        Doador doador = buscarDoadorPorId(doadorId);
        validarTextoObrigatorio(nome, "Nome do item");
        validarTextoObrigatorio(descricao, "Descricao do item");

        if (categoria == null) {
            throw new IllegalArgumentException("Categoria do item e obrigatoria.");
        }

        if (estadoConservacao == null) {
            throw new IllegalArgumentException("Estado de conservacao e obrigatorio.");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        ItemDoacao itemDoacao = new ItemDoacao(
                nome.trim(),
                categoria,
                descricao.trim(),
                quantidade,
                estadoConservacao,
                doador);
        return itemDoacaoRepository.save(itemDoacao);
    }

    public List<Doador> listarDoadores() {
        return doadorRepository.findAll();
    }

    public List<Beneficiario> listarBeneficiarios() {
        return beneficiarioRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Beneficiario::getNivelPrioridade).reversed())
                .toList();
    }

    public List<ItemDoacao> listarItens() {
        return itemDoacaoRepository.findAll();
    }

    public List<ItemDoacao> filtrarItensPorCategoria(CategoriaItem categoria) {
        return itemDoacaoRepository.findAll().stream()
                .filter(item -> item.getCategoria() == categoria)
                .toList();
    }

    public List<ItemDoacao> filtrarItensPorStatus(StatusItem status) {
        return itemDoacaoRepository.findAll().stream()
                .filter(item -> item.getStatus() == status)
                .toList();
    }

    public Doador buscarDoadorPorId(Long id) {
        return doadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doador com id " + id + " nao encontrado."));
    }

    private void validarTextoObrigatorio(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " e obrigatorio.");
        }
    }
}
