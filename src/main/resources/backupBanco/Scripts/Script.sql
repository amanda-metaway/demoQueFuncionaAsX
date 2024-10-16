update pessoa set senha = md5('123') where usuario = 'admin';
update configuracao set ws = 'N', intheabea = null, notchaauto = false, notpaipre = false, chavesms = null, ntrskey = null;
delete from formacontato;



ALTER ROLE postgres SET search_path TO public, aluno, auditoria, biblio, censo, merenda, patri, profe, soli, trans, temp, almoxarifado, vaga, apoio, financeiro, diario, tramite, esporte, procedimento;