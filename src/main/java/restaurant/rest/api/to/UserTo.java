package restaurant.rest.api.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restaurant.rest.api.model.AbstractBaseEntity;
import restaurant.rest.api.model.Role;
import restaurant.rest.api.model.User;
import restaurant.rest.api.model.Vote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "User's entity")
public class UserTo extends AbstractBaseTo {
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private LocalDateTime registered;
    private Set<Role> roles;
    private Set<VoteTo> votes;

    @Override
    public User toEntity() {
        User user = new User();
        user.setId(this.getId());
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setRoles(this.roles);
        user.setPassword(this.password);
        user.setEnabled(this.enabled);
        user.setVotes(convertToEntity());
        return user;
    }


    private Set<Vote> convertToEntity(){
        if(this.votes == null) return null;
        return this.votes.stream()
                .map(VoteTo::toEntity)
                .collect(Collectors.toSet());
    }




}
