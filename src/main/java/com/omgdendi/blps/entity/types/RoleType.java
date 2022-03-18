package com.omgdendi.blps.entity.types;

public enum RoleType {
    admin("ROLE_ADMIN"),
    moderator("ROLE_MODERATOR"),
    user("ROLE_USER");
    private String name;

    RoleType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
