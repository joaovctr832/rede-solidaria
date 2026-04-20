BEGIN;

INSERT INTO usuario (id, nome, telefone, email, endereco)
VALUES
    (1, 'Maria Silva', '(11) 99999-1000', 'maria.silva@email.com', 'Rua das Flores, 100'),
    (2, 'Instituto Esperanca', '(11) 98888-2000', 'contato@esperanca.org', 'Av. Central, 250'),
    (3, 'Carlos Souza', '(11) 97777-3000', 'carlos.souza@email.com', 'Rua do Sol, 45')
ON CONFLICT (id) DO NOTHING;

INSERT INTO doador (id)
VALUES (1)
ON CONFLICT (id) DO NOTHING;

INSERT INTO beneficiario (id, tipo, nivel_prioridade)
VALUES
    (2, 'ONG', 4),
    (3, 'FAMILIA', 5)
ON CONFLICT (id) DO NOTHING;

INSERT INTO item_doacao (
    id,
    doador_id,
    nome,
    categoria,
    descricao,
    quantidade,
    estado_conservacao,
    data_cadastro,
    status
)
VALUES
    (
        1,
        1,
        'Cesta Basica',
        'ALIMENTO_NAO_PERECIVEL',
        'Kit com arroz, feijao, oleo e macarrao.',
        10,
        'NOVO',
        CURRENT_DATE,
        'DISPONIVEL'
    )
ON CONFLICT (id) DO NOTHING;

INSERT INTO solicitacao (
    id,
    beneficiario_id,
    item_doacao_id,
    quantidade_solicitada,
    justificativa,
    status
)
VALUES
    (
        1,
        2,
        1,
        4,
        'Atendimento de familias cadastradas no projeto social.',
        'PENDENTE'
    )
ON CONFLICT (id) DO NOTHING;

INSERT INTO doacao_efetivada (
    id,
    item_doacao_id,
    doador_id,
    beneficiario_id,
    data,
    observacoes
)
VALUES
    (
        1,
        1,
        1,
        3,
        CURRENT_DATE,
        'Entrega piloto registrada para validacao do modelo.'
    )
ON CONFLICT (id) DO NOTHING;

SELECT setval(
    pg_get_serial_sequence('usuario', 'id'),
    COALESCE((SELECT MAX(id) FROM usuario), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('item_doacao', 'id'),
    COALESCE((SELECT MAX(id) FROM item_doacao), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('solicitacao', 'id'),
    COALESCE((SELECT MAX(id) FROM solicitacao), 1),
    true
);

SELECT setval(
    pg_get_serial_sequence('doacao_efetivada', 'id'),
    COALESCE((SELECT MAX(id) FROM doacao_efetivada), 1),
    true
);

COMMIT;
