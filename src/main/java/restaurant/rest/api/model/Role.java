package restaurant.rest.api.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    Role(String role){
        this.role = role;
    }


    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
