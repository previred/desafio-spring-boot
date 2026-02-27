package cl.previred.taskmanager.domain.model;

public record User(
        Long id,
        String username,
        String password
) {

    public User {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }

}
