package main;

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
import service.CadastroService;
import util.ConsoleHelper;

public class Main {
    private final CadastroService cadastroService;
    private final ConsoleHelper consoleHelper;

    public Main() {
        this.cadastroService = new CadastroService(
                new DoadorRepository(),
                new BeneficiarioRepository(),
                new ItemDoacaoRepository());
        this.consoleHelper = new ConsoleHelper();
    }

    public static void main(String[] args) {
        new Main().executar();
    }

    private void executar() {
        boolean executando = true;

        while (executando) {
            consoleHelper.imprimirCabecalho("Rede Solidaria - Checkpoint 1");
            System.out.println("1. Cadastrar doador");
            System.out.println("2. Cadastrar beneficiario");
            System.out.println("3. Cadastrar item para doacao");
            System.out.println("4. Listar doadores");
            System.out.println("5. Listar beneficiarios");
            System.out.println("6. Listar itens disponiveis");
            System.out.println("7. Filtrar itens por categoria");
            System.out.println("8. Filtrar itens por status");
            System.out.println("0. Sair");

            int opcao = consoleHelper.lerInteiro("Escolha uma opcao: ");

            try {
                switch (opcao) {
                    case 1 -> cadastrarDoador();
                    case 2 -> cadastrarBeneficiario();
                    case 3 -> cadastrarItem();
                    case 4 -> consoleHelper.imprimirLista("Doadores cadastrados", cadastroService.listarDoadores());
                    case 5 -> consoleHelper.imprimirLista(
                            "Beneficiarios cadastrados",
                            cadastroService.listarBeneficiarios());
                    case 6 -> consoleHelper.imprimirLista("Itens cadastrados", cadastroService.listarItens());
                    case 7 -> filtrarItensPorCategoria();
                    case 8 -> filtrarItensPorStatus();
                    case 0 -> {
                        executando = false;
                        System.out.println("Encerrando o sistema. Ate a proxima.");
                    }
                    default -> System.out.println("Opcao invalida. Escolha um item do menu.");
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Nao foi possivel concluir a operacao: " + exception.getMessage());
            }
        }
    }

    private void cadastrarDoador() {
        consoleHelper.imprimirCabecalho("Cadastro de doador");

        Doador doador = cadastroService.cadastrarDoador(
                consoleHelper.lerTexto("Nome: "),
                consoleHelper.lerTexto("Telefone: "),
                consoleHelper.lerTexto("Email: "),
                consoleHelper.lerTexto("Endereco: "));

        System.out.println("Doador cadastrado com sucesso: " + doador);
    }

    private void cadastrarBeneficiario() {
        consoleHelper.imprimirCabecalho("Cadastro de beneficiario");

        TipoBeneficiario tipo = consoleHelper.selecionarEnum(
                "Selecione o tipo de beneficiario",
                TipoBeneficiario.values());

        Beneficiario beneficiario = cadastroService.cadastrarBeneficiario(
                consoleHelper.lerTexto("Nome: "),
                consoleHelper.lerTexto("Telefone: "),
                consoleHelper.lerTexto("Email: "),
                consoleHelper.lerTexto("Endereco: "),
                tipo,
                consoleHelper.lerInteiro("Nivel de prioridade (1 a 5): "));

        System.out.println("Beneficiario cadastrado com sucesso: " + beneficiario);
    }

    private void cadastrarItem() {
        List<Doador> doadores = cadastroService.listarDoadores();
        if (doadores.isEmpty()) {
            throw new IllegalArgumentException("Cadastre pelo menos um doador antes de registrar itens.");
        }

        consoleHelper.imprimirLista("Selecione o doador responsavel", doadores);
        long doadorId = consoleHelper.lerLong("Informe o id do doador: ");
        CategoriaItem categoria = consoleHelper.selecionarEnum("Selecione a categoria", CategoriaItem.values());
        EstadoConservacao estadoConservacao = consoleHelper.selecionarEnum(
                "Selecione o estado de conservacao",
                EstadoConservacao.values());

        ItemDoacao itemDoacao = cadastroService.cadastrarItem(
                doadorId,
                consoleHelper.lerTexto("Nome do item: "),
                categoria,
                consoleHelper.lerTexto("Descricao: "),
                consoleHelper.lerInteiro("Quantidade: "),
                estadoConservacao);

        System.out.println("Item cadastrado com sucesso: " + itemDoacao);
    }

    private void filtrarItensPorCategoria() {
        CategoriaItem categoria = consoleHelper.selecionarEnum(
                "Filtrar por categoria",
                CategoriaItem.values());
        consoleHelper.imprimirLista(
                "Itens filtrados por categoria",
                cadastroService.filtrarItensPorCategoria(categoria));
    }

    private void filtrarItensPorStatus() {
        StatusItem statusItem = consoleHelper.selecionarEnum(
                "Filtrar por status",
                StatusItem.values());
        consoleHelper.imprimirLista(
                "Itens filtrados por status",
                cadastroService.filtrarItensPorStatus(statusItem));
    }
}
