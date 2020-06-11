package id.mobileprogramming.tugasproject5.models;

import java.util.List;

public class PariwisataStatus {
    private boolean status;
    private List<Pariwisata> message;

    public PariwisataStatus(boolean status, List<Pariwisata> message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public List<Pariwisata> getMessage() {
        return message;
    }
}
