package ee.olivervaga.plexnotify.plex.model.session;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("User")
public class User {

    @XStreamAlias("title")
    @XStreamAsAttribute
    private String name;

    @XStreamAlias("thumb")
    @XStreamAsAttribute
    private String thumbnail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
