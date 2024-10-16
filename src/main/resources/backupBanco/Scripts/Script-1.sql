update pessoa set senha = md5('123') where usuario = 'admin';
update configuracao set ws = 'N', intheabea = null, notchaauto = false, notpaipre = false, chavesms = null, ntrskey = null;
delete from formacontato;