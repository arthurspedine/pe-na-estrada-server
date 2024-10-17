CREATE TABLE t_pe_users
(
    id       BIGSERIAL       PRIMARY KEY,
    login    VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20) NOT NULL
);

CREATE TABLE t_pe_clients
(
    id         BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL NOT NULL,
    name       VARCHAR(90) NOT NULL,
    cpf        VARCHAR(11) NOT NULL UNIQUE,
    birth_date DATE        NOT NULL,
    FOREIGN KEY (user_id) REFERENCES t_pe_users (id)
);

CREATE TABLE t_pe_workshops
(
    id           BIGSERIAL PRIMARY KEY,
    user_id     BIGSERIAL NOT NULL,
    name         VARCHAR(90) NOT NULL,
    address      VARCHAR(100)  NOT NULL,
    number       INT           NOT NULL,
    zip_code     VARCHAR(9)    NOT NULL,
    neighborhood VARCHAR(100)  NOT NULL,
    city         VARCHAR(50)   NOT NULL,
    state        VARCHAR(50)   NOT NULL,
    rating       NUMERIC(3, 2) NOT NULL,
    maps_url      VARCHAR(200)  NOT NULL
);

CREATE TABLE t_pe_contact_phones
(
    id          BIGSERIAL PRIMARY KEY,
    ddi         INT NOT NULL,
    phone       INT NOT NULL,
    workshop_id BIGINT REFERENCES t_pe_workshops (id),
    client_id   BIGINT REFERENCES t_pe_clients (id)
);

CREATE TABLE t_pe_client_addresses
(
    id           BIGSERIAL PRIMARY KEY,
    client_id    BIGSERIAL    NOT NULL,
    street       VARCHAR(100) NOT NULL,
    number       INT          NOT NULL,
    complement   VARCHAR(50),
    neighborhood VARCHAR(100) NOT NULL,
    city         VARCHAR(50)  NOT NULL,
    state        VARCHAR(50)  NOT NULL,
    zip_code     VARCHAR(9)   NOT NULL,
    FOREIGN KEY (client_id) REFERENCES t_pe_clients (id)
);

CREATE TABLE t_pe_vehicles
(
    id            BIGSERIAL PRIMARY KEY,
    client_id     BIGINT      NOT NULL,
    brand         VARCHAR(40) NOT NULL,
    model         VARCHAR(40) NOT NULL,
    year          INT         NOT NULL,
    license_plate VARCHAR(7)  NOT NULL,
    FOREIGN KEY (client_id) REFERENCES t_pe_clients (id)
);

CREATE TABLE t_pe_diagnostico
(
    id_diagnostico BIGSERIAL PRIMARY KEY,
    id_orcamento   INT           NOT NULL,
    ds_descricao   VARCHAR(1200) NOT NULL
);

CREATE TABLE t_pe_orcamento
(
    id_orcamento BIGSERIAL PRIMARY KEY,
    id_oficina   INT  NOT NULL,
    id_veiculo   INT  NOT NULL,
    dt_inicio    DATE NOT NULL,
    dt_entrega   DATE NOT NULL,
    dt_finalizao DATE
);

CREATE TABLE t_pe_peca
(
    id_peca   BIGSERIAL PRIMARY KEY,
    nm_peca   VARCHAR(90) NOT NULL,
    cd_modelo VARCHAR(50) NOT NULL
);

CREATE TABLE t_pe_servico
(
    id_servico     BIGSERIAL PRIMARY KEY,
    id_diagnostico INT           NOT NULL,
    id_peca        INT,
    ds_servico     VARCHAR(300)  NOT NULL,
    vl_peca        NUMERIC(9, 2),
    vl_mao_obra    NUMERIC(9, 2) NOT NULL
);

-- ALTER TABLE t_pe_diagnostico
--     ADD CONSTRAINT fk_orcamento_diagnostico FOREIGN KEY (id_orcamento) REFERENCES t_pe_orcamento (id_orcamento);
--
-- ALTER TABLE t_pe_servico
--     ADD CONSTRAINT fk_pe_diagnostico_servico FOREIGN KEY (id_diagnostico) REFERENCES t_pe_diagnostico (id_diagnostico);
--
-- ALTER TABLE t_pe_orcamento
--     ADD CONSTRAINT fk_pe_oficina_orcamento FOREIGN KEY (id_oficina) REFERENCES t_pe_oficina (id_oficina);
--
-- ALTER TABLE t_pe_servico
--     ADD CONSTRAINT fk_pe_peca_servico FOREIGN KEY (id_peca) REFERENCES t_pe_peca (id_peca);
--
-- ALTER TABLE t_pe_orcamento
--     ADD CONSTRAINT fk_pe_veiculo_orcamento FOREIGN KEY (id_veiculo) REFERENCES t_pe_veiculo (id_veiculo);
