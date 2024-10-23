package com.example.demo.Converter;

import com.example.demo.Model.TipoServico;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class TipoServicoConverter implements Converter {
    //obj aqui
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        int tipoServicoId = Integer.parseInt(value);
        return TipoServico.fromTiposervico(tipoServicoId);
    }

    //aqui o q precisaajustar
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        //  enum  inteiro
        if (value instanceof TipoServico) {
            return String.valueOf(((TipoServico) value).getTipoServico());
        }

        return "";
    }

}

