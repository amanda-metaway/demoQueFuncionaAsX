CREATE TABLE pets (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    raca VARCHAR(50) NOT NULL,
    dono VARCHAR(100) NOT NULL,
    dono_contato VARCHAR(15) NOT NULL
);
select * from agendamentos a  ;
select dono_contato from pets p where dono_contato notnull ;
select * from pets p ;
select * from perfil p; 

select * from users u ;


SELECT * FROM pg_sequences WHERE sequencename = 'pets_id_seq';

SELECT nextval('pets_id_seq');

SELECT setval('pets_id_seq', (SELECT MAX(id) FROM pets));

ALTER TABLE users ADD COLUMN perfil;
insert into users (perfil)VALUES ('ADMIN');

CREATE TABLE perfil (
    id serial PRIMARY KEY,
    tipo varchar() NOT NULL 
);


CREATE TABLE auditoria (
    id int PRIMARY KEY,
    user_id INT NOT NULL,
    acao VARCHAR(255) NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


select * from auditoria a;




INSERT INTO perfil (nome) VALUES ('ADMIN');
INSERT INTO perfil (nome) VALUES ('CLIENTE');
insert into users (cpfUsuario)

CREATE TABLE users (
    id serial PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    perfil_id INT,
    FOREIGN KEY (perfil_id) REFERENCES perfil(id)
)
CREATE TABLE agendamentos (
    id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    servico VARCHAR(255) NOT NULL,
    pet_id INT,
    FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE
);

drop  table perfil  ;

