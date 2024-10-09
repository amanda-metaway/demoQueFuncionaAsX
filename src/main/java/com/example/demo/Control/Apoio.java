package com.example.demo.Control;

import com.example.demo.Model.UserProfile;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public class Apoio {

    private List<SelectItem> list;

    public List<SelectItem> getList() {
        if (list == null) {
            list = new ArrayList<>();
            list.add(new SelectItem(UserProfile.ADMIN, UserProfile.ADMIN.getPerfil()));
            list.add(new SelectItem(UserProfile.CLIENTE, UserProfile.CLIENTE.getPerfil()));
        }
        return list;
    }
}
