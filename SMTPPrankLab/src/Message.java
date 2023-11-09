public class Message {
    private final String subject;
    private final String body;

    public Message(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Subject: " + subject + "\n" + "Body: " + body;
    }
}
