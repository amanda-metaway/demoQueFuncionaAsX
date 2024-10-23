package com.example.demo.Control;

import com.example.demo.Model.TipoServico;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public class ServicosApoio {



    private List<SelectItem> list;

    public List<SelectItem> getList() {
        if (list == null) {
            list = new ArrayList<>();
            for (TipoServico tipo : TipoServico.values()) {
                list.add(new SelectItem(tipo.getTipoServico(), tipo.name()));
            }
        }
        return list;
    }
}
