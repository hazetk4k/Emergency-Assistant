package com.example.server.DBTransactions;

public class FulliKindRep {
    private Integer kind_id;
    private String kind_name;
    private String char_name;

    public Integer getKind_id() {
        return kind_id;
    }

    public void setKind_id(Integer kind_id) {
        this.kind_id = kind_id;
    }

    public String getKind_name() {
        return kind_name;
    }

    public void setKind_name(String kind_name) {
        this.kind_name = kind_name;
    }

    public String getChar_name() {
        return char_name;
    }

    public void setChar_name(String char_name) {
        this.char_name = char_name;
    }

    public FulliKindRep(Integer kind_id, String kind_name, String char_name) {
        this.kind_id = kind_id;
        this.kind_name = kind_name;
        this.char_name = char_name;
    }
}
