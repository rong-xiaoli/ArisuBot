package top.rongxiaoli.plugins.OsuBot.backend.osuAPI.exceptions;

public class TooManyRequestsException extends Exception {
    public int count;
    public TooManyRequestsException(int count) {
        this.count = count;
    }
    public TooManyRequestsException(int count, String message) {
        super(message);
        this.count = count;
    }
    public TooManyRequestsException(int count, String message, Throwable cause) {
        super(message, cause);
        this.count = count;
    }
}
