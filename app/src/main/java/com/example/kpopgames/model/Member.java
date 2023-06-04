package com.example.kpopgames.model;

public class Member {

    private  long id;
    private String name;
    private String gruppe;

    public Member(int id, String name, String gruppe) {
        this.id = id;
        this.name = name;
        this.gruppe = gruppe;
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

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gruppe='" + gruppe + '\'' +
                '}';
    }
}
