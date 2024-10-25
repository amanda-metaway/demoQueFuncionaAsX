package com.example.demo.api;

import com.example.demo.Model.User;

public class ClienteRestImpl implements ClienteRest {
    @Override
    public User getCliente(Integer id) {
        return new User();
    }
}
