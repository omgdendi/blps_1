package com.omgdendi.blps.entity.types;

import lombok.Getter;

@Getter
public enum PrivilegeType {
    read("PRIVILEGE_READ"),
    write("PRIVILEGE_WRITE");

    private String name;
    PrivilegeType(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
