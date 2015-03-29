package ee.olivervaga.plexnotify.plex.model.status;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("MediaContainer")
public class PlexStatusResponse {

    @XStreamAlias("friendlyName")
    @XStreamAsAttribute
    private String friendlyName;

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
