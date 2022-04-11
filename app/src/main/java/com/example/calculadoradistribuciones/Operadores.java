package com.example.calculadoradistribuciones;

public enum Operadores {
    IGUAL("="),
    MENORIGUAL("<="),
    MENOR("<"),
    MAYORIGUAL(">="),
    MAYOR(">"),
    DISTINTO("!=");

    private String simbolo;

    public String getSimbolo(){
        return this.simbolo;
    }

    Operadores(String s) {
        this.simbolo = s;
    }
}
