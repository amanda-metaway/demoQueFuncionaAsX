package com.example.demo.Model;

public enum UserProfile {
    ADMIN("admin"),
    CLIENTE("cliente");

    private String perfil;

    UserProfile(String perfil) {
        this.perfil = perfil;
    }

    public String getPerfil() {
        return perfil;
    }

    public static UserProfile fromValue(String value) {
        for (UserProfile profile : UserProfile.values()) {
            if (profile.perfil.equals(value)) {
                return profile;
            }
        }
        throw new IllegalArgumentException("No enum constant for value " + value);
    }
}
