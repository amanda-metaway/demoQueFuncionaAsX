package com.example.demo.Model;

public enum TipoServico {
    BANHO(1),
    TOSA(2),
    CONSULTA(3),
    VACINACAO(4);


    private final int tipoServico;



    TipoServico(int tipoServico) {
        this.tipoServico = tipoServico;
    }



    public static TipoServico fromTiposervico(int tipoServico) {
        for (TipoServico tipo : TipoServico.values()) {
            if (tipo.getTipoServico() == tipoServico) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("TipoServico não encontrado para ID: " + tipoServico);
    }



    public int getTipoServico() {
        return tipoServico;
    }

}
