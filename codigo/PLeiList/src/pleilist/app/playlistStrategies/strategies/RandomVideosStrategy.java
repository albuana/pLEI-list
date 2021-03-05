package pleilist.app.playlistStrategies.strategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import pleilist.app.dominio.videos.Video;
import pleilist.app.playlistStrategies.PickVideosStrategy;

public class RandomVideosStrategy implements PickVideosStrategy {

	@Override
	public List<Video> pickVideos(int nVideos, List<Video> videos) {
		Random r = new Random();
		int index; 
		Set<Video> result = new HashSet<>();

		while(result.size() < nVideos && result.size() < videos.size()) {
			index = r.nextInt(videos.size());
			result.add(videos.get(index));
		}
		return new ArrayList<>(result);
	}
}
