package mohammaddarwaish.com.github.userservice.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    private String forename;

    @NotBlank
    @Size(min = 3, max = 50)
    private String surname;

    @NotBlank
    @Email
    private String email;

}
