package sh.ricky.core.exception;

@SuppressWarnings("serial")
public class ForwardException extends RuntimeException {
    public ForwardException() {
    }

    public ForwardException(Throwable ex) {
        super(ex);
    }

    public ForwardException(String msg) {
        super(msg);
    }

    public ForwardException(String msg, Throwable ex) {
        super(msg, ex);
    }
}