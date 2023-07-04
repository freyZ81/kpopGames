package com.example.kpopgames.model;

public class Member {

    private  long id;
    private String name;
    private String gruppe;
    private String geburtstag;

    public Member(int id, String name, String gruppe, String geburtstag) {
        this.id = id;
        this.name = name;
        this.gruppe = gruppe;
        this.geburtstag = geburtstag;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    public String getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(String geburtstag) {
        this.geburtstag = geburtstag;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gruppe='" + gruppe + '\'' +
                '}';
    }
}
