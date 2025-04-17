package rmit.edu.vn.hcmc_metro.userauth;

class DtoLogin {
    protected String email;
    protected String password;

    protected DtoLogin() {}

    protected DtoLogin(String username, String password) {
        this.email = username;
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}


