package com.omgdendi.blps.entity.types;

public enum EssayStatus {
    approved("APPROVED"),
    not_approved("NOT_APPROVED"),
    failed("FAILED"),
    checking("CHECKING");

    private String name;

    EssayStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
