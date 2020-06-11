package id.mobileprogramming.tugasproject5.models;

public class Status {
    private boolean status;
    private String message;

    public Status(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
