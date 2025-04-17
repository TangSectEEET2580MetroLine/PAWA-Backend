package rmit.edu.vn.hcmc_metro.security_config;

public enum RoleConfig {
    PASSENGER("PASSENGER"),
    ADMIN("ADMIN"),
    GUEST("GUEST"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String roleName;

    RoleConfig(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
