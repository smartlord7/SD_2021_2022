public class SharedMessage {
    private String message;

    public SharedMessage() {

    }

    public SharedMessage(String message) {
        this.message = message;
    }

    public synchronized String getMessage() {
        return message;
    }

    public synchronized void setMessage(String message) {
        synchronized (this) {
            this.message = message;
            this.notifyAll();
        }
    }

    @Override
    public String toString() {
        return "SharedMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
