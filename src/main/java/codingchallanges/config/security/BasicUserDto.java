package codingchallanges.config.security;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class BasicUserDto {

    @NotEmpty
    @NotNull
    @Length(min = 3, max = 30)
    private String username;

    @NotEmpty
    @NotNull
    @Length(min = 8, max = 30)
    private String password;

    @NotEmpty
    @NotNull
    @Length(min = 8, max = 30)
    private String matchingPassword;

}
