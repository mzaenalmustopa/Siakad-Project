package mzaenalmstpa.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import mzaenalmstpa.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserModel {
    private String id;
    @NotBlank
    @NotEmpty
    private String username;
    @NotBlank
    @NotEmpty
    private String password;
    @NotBlank
    @NotEmpty
    private String email;
    @NotBlank
    @NotEmpty
    private String role;
    private List<String> roles = new ArrayList<>();

    public UserModel() {
    }

    public UserModel(UserEntity data) {
        this.id = data.getId();
        this.username = data.getUsername();
        this.password = "";
        this.email = data.getEmail();
        if (data.getRoles().size() > 0){
            this.roles = data.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList());
            this.role  = new ArrayList<>(data.getRoles()).get(0).getName();
        }
    }
}
