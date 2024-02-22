package com.bueno.rinhabueno.entity;

import lombok.Getter;
import lombok.NonNull;

@Getter
public enum TypeTransaction {

    C,D;
    public final boolean isEqualOf(@NonNull String type){
        return type.equalsIgnoreCase(this.toString());
    }

}
