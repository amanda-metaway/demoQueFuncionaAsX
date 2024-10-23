select * from servicos s order by id desc;
select * from tipos_servico ts;


=====

/*
 * testes
 * */
/*
 * servico agendado/usadopor usuario*/
SELECT s.user_id, t.nome, t.valor, t.duracao_estimado
FROM servicos s
JOIN tipos_servico t ON s.tipo_servico = t.id
ORDER BY s.user_id;


/*
 * total por usuario*/
SELECT s.user_id, SUM(t.valor) AS total_gasto
FROM servicos s
JOIN tipos_servico t ON s.tipo_servico = t.id
GROUP BY s.user_id;

/*
 * servicos disponiveis e seus valores*/
SELECT nome, valor, duracao_estimado
FROM tipos_servico;

/*Contar Quantos Serviços Cada Tipo Foi Agendado: Para saber a popularidade de cada tipo de serviço.
 *
 * */
SELECT t.nome, COUNT(s.id) AS quantidade_agendamentos
FROM tipos_servico t
LEFT JOIN servicos s ON t.id = s.tipo_servico
GROUP BY t.id;

/*
 * Duração Total de Serviços por Usuário: Para calcular a duração total dos serviços agendados por um usuário específico.
 * */
SELECT s.user_id, SUM(t.duracao_estimado) AS duracao_total
FROM servicos s
JOIN tipos_servico t ON s.tipo_servico = t.id
GROUP BY s.user_id;


