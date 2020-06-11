package id.mobileprogramming.tugasproject5.models;

public class LoginStatus {
    private boolean status;
    private Account message;

    public LoginStatus(boolean status, Account message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public Account getMessage() {
        return message;
    }
}
