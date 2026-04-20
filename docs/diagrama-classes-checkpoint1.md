# Diagrama de Classes - Checkpoint 1

classDiagram
    class Usuario {
        <<abstract>>
        -Long id
        -String nome
        -String telefone
        -String email
        -String endereco
        +getId() Long
        +setId(id Long) void
        +getNome() String
        +setNome(nome String) void
        +getTelefone() String
        +setTelefone(telefone String) void
        +getEmail() String
        +setEmail(email String) void
        +getEndereco() String
        +setEndereco(endereco String) void
        #resumoBasico() String
    }

    class Doador {
        -List~ItemDoacao~ itensDoacao
        -List~DoacaoEfetivada~ doacoesEfetivadas
        +getItensDoacao() List~ItemDoacao~
        +adicionarItemDoacao(itemDoacao ItemDoacao) void
        +removerItemDoacao(itemDoacao ItemDoacao) void
        +getDoacoesEfetivadas() List~DoacaoEfetivada~
        +adicionarDoacaoEfetivada(doacaoEfetivada DoacaoEfetivada) void
        +removerDoacaoEfetivada(doacaoEfetivada DoacaoEfetivada) void
    }

    class Beneficiario {
        -TipoBeneficiario tipo
        -int nivelPrioridade
        -List~Solicitacao~ solicitacoes
        -List~DoacaoEfetivada~ doacoesEfetivadas
        +getTipo() TipoBeneficiario
        +setTipo(tipo TipoBeneficiario) void
        +getNivelPrioridade() int
        +setNivelPrioridade(nivelPrioridade int) void
        +getSolicitacoes() List~Solicitacao~
        +adicionarSolicitacao(solicitacao Solicitacao) void
        +removerSolicitacao(solicitacao Solicitacao) void
        +getDoacoesEfetivadas() List~DoacaoEfetivada~
        +adicionarDoacaoEfetivada(doacaoEfetivada DoacaoEfetivada) void
        +removerDoacaoEfetivada(doacaoEfetivada DoacaoEfetivada) void
    }

    class ItemDoacao {
        -Long id
        -String nome
        -CategoriaItem categoria
        -String descricao
        -int quantidade
        -EstadoConservacao estadoConservacao
        -LocalDate dataCadastro
        -StatusItem status
        -Doador doador
        -List~Solicitacao~ solicitacoes
        -List~DoacaoEfetivada~ doacoesEfetivadas
        +getDoador() Doador
        +setDoador(doador Doador) void
        +getSolicitacoes() List~Solicitacao~
        +adicionarSolicitacao(solicitacao Solicitacao) void
        +removerSolicitacao(solicitacao Solicitacao) void
        +getDoacoesEfetivadas() List~DoacaoEfetivada~
        +adicionarDoacaoEfetivada(doacaoEfetivada DoacaoEfetivada) void
        +removerDoacaoEfetivada(doacaoEfetivada DoacaoEfetivada) void
    }

    class Solicitacao {
        -Long id
        -Beneficiario beneficiario
        -ItemDoacao itemDoacao
        -int quantidadeSolicitada
        -String justificativa
        -StatusSolicitacao status
        +getBeneficiario() Beneficiario
        +setBeneficiario(beneficiario Beneficiario) void
        +getItemDoacao() ItemDoacao
        +setItemDoacao(itemDoacao ItemDoacao) void
    }

    class DoacaoEfetivada {
        -Long id
        -ItemDoacao itemDoacao
        -Doador doador
        -Beneficiario beneficiario
        -LocalDate data
        -String observacoes
        +getItemDoacao() ItemDoacao
        +setItemDoacao(itemDoacao ItemDoacao) void
        +getDoador() Doador
        +setDoador(doador Doador) void
        +getBeneficiario() Beneficiario
        +setBeneficiario(beneficiario Beneficiario) void
    }

    class TipoBeneficiario {
        <<enumeration>>
        FAMILIA
        ONG
        ESCOLA
        ABRIGO
        COMUNIDADE
    }

    class CategoriaItem {
        <<enumeration>>
        ALIMENTO_NAO_PERECIVEL
        ROUPA
        MATERIAL_ESCOLAR
        MOVEL
        HIGIENE
        OUTROS
    }

    class EstadoConservacao {
        <<enumeration>>
        NOVO
        BOM
        USADO
        PRECISA_REPARO
    }

    class StatusItem {
        <<enumeration>>
        DISPONIVEL
        RESERVADO
        ENTREGUE
        CANCELADO
    }

    class StatusSolicitacao {
        <<enumeration>>
        PENDENTE
        APROVADA
        REJEITADA
        CONCLUIDA
    }

    Usuario <|-- Doador
    Usuario <|-- Beneficiario
    Beneficiario --> "1" TipoBeneficiario
    ItemDoacao --> "1" CategoriaItem
    ItemDoacao --> "1" EstadoConservacao
    ItemDoacao --> "1" StatusItem
    Doador "1" --> "0..*" ItemDoacao : cadastra
    Beneficiario "1" --> "0..*" Solicitacao : realiza
    ItemDoacao "1" --> "0..*" Solicitacao : recebe
    Solicitacao --> "1" StatusSolicitacao
    Doador "1" --> "0..*" DoacaoEfetivada : efetiva
    Beneficiario "1" --> "0..*" DoacaoEfetivada : recebe
    ItemDoacao "1" --> "0..*" DoacaoEfetivada : compoe
