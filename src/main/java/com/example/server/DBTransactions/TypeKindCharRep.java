package com.example.server.DBTransactions;

import java.util.List;

public class TypeKindCharRep {
    private String char_name;
    private String kind_name;
    private String name;
    private String recommendation;
    private List<String> services;

    public String getChar_name() {
        return char_name;
    }

    public void setChar_name(String char_name) {
        this.char_name = char_name;
    }

    public String getKind_name() {
        return kind_name;
    }

    public void setKind_name(String kind_name) {
        this.kind_name = kind_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public TypeKindCharRep(String char_name, String kind_name, String name, String recommendation, List<String> services) {
        this.char_name = char_name;
        this.kind_name = kind_name;
        this.name = name;
        this.recommendation = recommendation;
        this.services = services;
    }

}
