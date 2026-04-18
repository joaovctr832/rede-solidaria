# Rede Solidaria

Aplicacao Java orientada a objetos para apoiar o cadastro, o gerenciamento e a distribuicao de itens doados para pessoas e instituicoes em situacao de necessidade.

## Checkpoint 1

Entrega atual:

- estrutura inicial do projeto em Java
- modelagem das principais classes do dominio
- menu principal funcional em terminal
- cadastro basico de doador, beneficiario e item
- listagens e filtros simples para apoiar a demonstracao

## Objetivo social

O projeto foi pensado para organizar doacoes de forma mais rastreavel e responsavel, alinhando-se especialmente aos ODS 1, 10 e 12.

## Estrutura de pastas

```text
src/
|-- main/
|   `-- Main.java
|-- model/
|   |-- Beneficiario.java
|   |-- CategoriaItem.java
|   |-- DoacaoEfetivada.java
|   |-- Doador.java
|   |-- EstadoConservacao.java
|   |-- Identificavel.java
|   |-- ItemDoacao.java
|   |-- Solicitacao.java
|   |-- StatusItem.java
|   |-- StatusSolicitacao.java
|   |-- TipoBeneficiario.java
|   `-- Usuario.java
|-- repository/
|   |-- BeneficiarioRepository.java
|   |-- DoadorRepository.java
|   |-- InMemoryRepository.java
|   `-- ItemDoacaoRepository.java
|-- service/
|   `-- CadastroService.java
`-- util/
    `-- ConsoleHelper.java
```

## Como executar

No PowerShell:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files
java -cp out main.Main
```

## Fluxo disponivel no checkpoint 1

1. cadastrar doadores
2. cadastrar beneficiarios
3. cadastrar itens de doacao vinculados a um doador
4. listar doadores, beneficiarios e itens cadastrados
5. filtrar itens por categoria ou status

## Diagrama de classes

O diagrama simplificado desta etapa esta em [docs/diagrama-classes-checkpoint1.md](docs/diagrama-classes-checkpoint1.md).

## Exemplo rapido de execucao

```text
====================================
Rede Solidaria - Checkpoint 1
====================================
1. Cadastrar doador
2. Cadastrar beneficiario
3. Cadastrar item para doacao
4. Listar doadores
5. Listar beneficiarios
6. Listar itens disponiveis
7. Filtrar itens por categoria
8. Filtrar itens por status
0. Sair
```

## Proximos passos

- implementar solicitacoes de itens
- aplicar regras de negocio para aprovacao e indisponibilidade
- persistir dados em arquivo
- gerar relatorios de doacoes efetivadas

