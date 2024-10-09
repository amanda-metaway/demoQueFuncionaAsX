package com.example.demo.Converter;

import com.example.demo.Model.UserProfile;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class UserProfileConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value != null ? UserProfile.valueOf(value) : null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String retorno = null;
        if (value instanceof UserProfile) {
            retorno = ((UserProfile) value).name();
        }
        return retorno;
    }
}
