package com.example.gapoclone.Model;

public class React {
    private String idPerson;
    private String typeReact;

    public React(String idPerson, String typeReact) {
        this.idPerson = idPerson;
        this.typeReact = typeReact;
    }

    public React() {
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    public String getTypeReact() {
        return typeReact;
    }

    public void setTypeReact(String typeReact) {
        this.typeReact = typeReact;
    }

    @Override
    public String toString() {
        return "React{" +
                "idPerson='" + idPerson + '\'' +
                ", typeReact='" + typeReact + '\'' +
                '}';
    }
}
