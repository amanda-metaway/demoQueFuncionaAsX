CREATE TABLE public.agendamentos
(
    id         serial4   NOT NULL,
    servico_id int4      NOT NULL,
    user_id    int4      NOT NULL,
    data_hora  timestamp NOT NULL,
    status public.status_agendamento DEFAULT 'AGENDADO'::status_agendamento NULL,
    CONSTRAINT agendamentos_pkey PRIMARY KEY (id),
    CONSTRAINT fk_servico FOREIGN KEY (servico_id) REFERENCES public.servicos (id) ON DELETE CASCADE,
    CONSTRAINT fk_usuario FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE
);

CREATE TABLE public.auditoria
(
    id        serial4      NOT NULL,
    acao      varchar(255) NOT NULL,
    data_hora timestamp    NOT NULL,
    user_id   int4 NULL,
    CONSTRAINT auditoria_pkey PRIMARY KEY (id),
    CONSTRAINT auditoria_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.pets
(
    id      serial4      NOT NULL,
    nome    varchar(100) NOT NULL,
    raca    varchar(50)  NOT NULL,
    user_id int4         NOT NULL,
    CONSTRAINT pets_pkey PRIMARY KEY (id),
    CONSTRAINT pets_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE public.servicos
(
    id           serial4 NOT NULL,
    tipo_servico int4    NOT NULL,
    user_id      int4    NOT NULL,
    CONSTRAINT servicos_pkey PRIMARY KEY (id),
    CONSTRAINT fk_tipo_servico FOREIGN KEY (tipo_servico) REFERENCES public.tipos_servico (id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE
);

CREATE TABLE public.tipos_servico
(
    id    serial4      NOT NULL,
    nome  varchar(255) NOT NULL,
    valor numeric(10, 2) NULL,
    duracao_estimado interval NULL,
    CONSTRAINT tipos_servico_pkey PRIMARY KEY (id)
);

CREATE TABLE public.users
(
    id         serial4      NOT NULL,
    "name"     varchar(100) NOT NULL,
    cpfusuario varchar NULL,
    "password" varchar NULL,
    perfil     varchar NULL,
    contato    varchar(15) NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);