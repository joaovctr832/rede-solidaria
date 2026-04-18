package util;

import java.util.List;
import java.util.Scanner;

public class ConsoleHelper {
    private final Scanner scanner;

    public ConsoleHelper() {
        this.scanner = new Scanner(System.in);
    }

    public void imprimirCabecalho(String titulo) {
        System.out.println();
        System.out.println("====================================");
        System.out.println(titulo);
        System.out.println("====================================");
    }

    public int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine();

            try {
                return Integer.parseInt(valor.trim());
            } catch (NumberFormatException exception) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    public long lerLong(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine();

            try {
                return Long.parseLong(valor.trim());
            } catch (NumberFormatException exception) {
                System.out.println("Digite um identificador numerico valido.");
            }
        }
    }

    public String lerTexto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = scanner.nextLine();

            if (!valor.isBlank()) {
                return valor.trim();
            }

            System.out.println("Esse campo nao pode ficar vazio.");
        }
    }

    public <T> void imprimirLista(String titulo, List<T> itens) {
        imprimirCabecalho(titulo);

        if (itens.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }

        itens.forEach(System.out::println);
    }

    public <E extends Enum<E>> E selecionarEnum(String titulo, E[] opcoes) {
        imprimirCabecalho(titulo);

        for (int i = 0; i < opcoes.length; i++) {
            System.out.printf("%d. %s%n", i + 1, opcoes[i]);
        }

        while (true) {
            int escolha = lerInteiro("Escolha uma opcao: ");
            if (escolha >= 1 && escolha <= opcoes.length) {
                return opcoes[escolha - 1];
            }

            System.out.println("Opcao invalida. Tente novamente.");
        }
    }
}

