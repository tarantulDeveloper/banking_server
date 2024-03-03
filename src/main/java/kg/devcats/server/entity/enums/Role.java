package kg.devcats.server.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN("Администратор"),
    ROLE_USER("Пользователь"),
    ROLE_MANAGER("Менеджер");

    private final String russianRepresentation;

    private Role(String russianRepresentation) {
        this.russianRepresentation = russianRepresentation;
    }

    public String getRussianRepresentation() {
        return russianRepresentation;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
