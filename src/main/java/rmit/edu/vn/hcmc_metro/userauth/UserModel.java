package rmit.edu.vn.hcmc_metro.userauth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users") // Specify the MongoDB collection name
public class UserModel {

    @Id
    private String id; // MongoDB uses String IDs by default
    
    @Indexed(unique = true)
    private String email;
    
    private String password;
    private boolean enabled;
    private String role;

    public UserModel(String email, String password, String role, boolean enabled) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }

    public UserModel() {
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserModel [id=" + id + ", email=" + email + ", password=" + password + ", enabled=" + enabled + ", role=" + role + "]";
    }
}