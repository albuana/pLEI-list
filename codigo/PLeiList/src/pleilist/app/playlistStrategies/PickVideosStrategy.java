package pleilist.app.playlistStrategies;

import java.util.List;

import pleilist.app.dominio.videos.Video;

public interface PickVideosStrategy {
    public List<Video> pickVideos(int nVideos, List<Video> videos);
}