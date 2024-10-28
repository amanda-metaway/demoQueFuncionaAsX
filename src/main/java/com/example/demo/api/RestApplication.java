package com.example.demo.api;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestApplication extends Application {
    private final Set<Class<?>> classes;


    public RestApplication() {
        classes = new HashSet<>();
        classes.add(UserRestImpl.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
