package rmit.edu.vn.hcmc_metro.userauth;

class DtoAuthResponse {
    private String token;

    protected DtoAuthResponse() {}

    protected DtoAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
