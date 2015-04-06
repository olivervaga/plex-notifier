package ee.olivervaga.plexnotify.notifier.pushbullet.model;

public class Note {

    private String type = "note";

    private String title;

    private String body;

    public Note(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
