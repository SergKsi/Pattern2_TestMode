package api;

import lombok.Value;

@Value
public class RegisterUserData {
    private String login;
    private String password;
    private String status;
}
