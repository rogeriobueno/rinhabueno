package com.bueno.rinhabueno.entity;

public enum TypeTransaction {
    C, D;

    public boolean isEqualIgnoreCase(String tipo) {
        return tipo != null && tipo.equalsIgnoreCase(this.toString());
    }
}
