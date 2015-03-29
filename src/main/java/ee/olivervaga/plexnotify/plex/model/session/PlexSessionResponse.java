package ee.olivervaga.plexnotify.plex.model.session;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("MediaContainer")
public class PlexSessionResponse {

    @XStreamImplicit(itemFieldName = "Video")
    List<Video> videos;

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
