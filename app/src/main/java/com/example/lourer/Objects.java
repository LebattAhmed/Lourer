package com.example.lourer;

public class Objects {

    private String userId;
    private String nom;
    private String description;
    private String numero;

    public Objects() {
    }

    public Objects(String userId, String nom, String description, String numero) {
        this.userId = userId;
        this.nom = nom;
        this.description = description;
        this.numero = numero;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Objects{" +
                "userId='" + userId + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}
