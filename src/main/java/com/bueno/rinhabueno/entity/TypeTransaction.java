package com.bueno.rinhabueno.entity;

public enum TypeTransaction {
    c, d;

    public boolean isEqualIgnoreCase(String tipo) {
        return tipo != null && tipo.equalsIgnoreCase(this.toString());
    }
}
