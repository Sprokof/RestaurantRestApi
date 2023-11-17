package restaurant.rest.api.model;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String role;

    Role(String role){
        this.role = role;
    }


}
