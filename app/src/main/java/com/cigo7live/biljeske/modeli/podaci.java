package com.cigo7live.biljeske.modeli;


public class podaci {

    private int id;
    private String label;
    private String note;

    public podaci() {
    }

    public podaci(int id, String label, String note) {
        this.id = id;
        this.label = label;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return label;
    }

    public void setNaslov(String label) {
        this.label = label;
    }

    public String getBiljeska() {
        return note;
    }

    public void setBiljeska(String note) {
        this.note = note;
    }
}
