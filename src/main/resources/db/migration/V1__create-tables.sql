CREATE TABLE t_pe_users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20)  NOT NULL
);

CREATE TABLE t_pe_clients
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGSERIAL   NOT NULL,
    name       VARCHAR(90) NOT NULL,
    cpf        VARCHAR(11) NOT NULL UNIQUE,
    birth_date DATE        NOT NULL,
    FOREIGN KEY (user_id) REFERENCES t_pe_users (id)
);

CREATE TABLE t_pe_workshops
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGSERIAL     NOT NULL,
    name         VARCHAR(90)   NOT NULL,
    address      VARCHAR(100)  NOT NULL,
    number       INT           NOT NULL,
    zip_code     VARCHAR(9)    NOT NULL,
    neighborhood VARCHAR(100)  NOT NULL,
    city         VARCHAR(50)   NOT NULL,
    state        VARCHAR(50)   NOT NULL,
    rating       NUMERIC(3, 2) NOT NULL,
    maps_url     VARCHAR(200)  NOT NULL
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

CREATE TABLE t_pe_estimates
(
    id                  BIGSERIAL PRIMARY KEY,
    workshop_id         BIGSERIAL    NOT NULL,
    vehicle_id          BIGSERIAL    NOT NULL,
    initial_description VARCHAR(500) NOT NULL,
    scheduled_at        TIMESTAMP    NOT NULL,
    created_at          TIMESTAMP    NOT NULL,
    value               NUMERIC(10, 2),
    finished_at         TIMESTAMP,
    FOREIGN KEY (workshop_id) REFERENCES t_pe_workshops (id),
    FOREIGN KEY (vehicle_id) REFERENCES t_pe_vehicles (id)
);

CREATE TABLE t_pe_services
(
    id          BIGSERIAL PRIMARY KEY,
    estimate_id BIGSERIAL      NOT NULL,
    description VARCHAR(500)   NOT NULL,
    price       NUMERIC(10, 2) NOT NULL,
    created_at  TIMESTAMP      NOT NULL,
    FOREIGN KEY (estimate_id) REFERENCES t_pe_estimates (id)
);
