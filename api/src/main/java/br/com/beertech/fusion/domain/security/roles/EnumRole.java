package br.com.beertech.fusion.domain.security.roles;

public enum EnumRole {

    ROLE_USER(1),
    ROLE_MODERATOR(2);

    public int id;
    EnumRole(int value) {
        id = value;
    }

    public static EnumRole getByName(String ROLE) {
        if (ROLE.equals(ROLE_USER.toString())) {
            return ROLE_USER;
        } else {
            return ROLE_MODERATOR;
        }
    }
}
