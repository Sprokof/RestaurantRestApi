package restaurant.rest.api.model;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    Role(String role){
        this.role = role;
    }


}
