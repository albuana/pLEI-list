package pleilist.app.playlistStrategies.strategies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import pleilist.app.dominio.videos.Video;
import pleilist.app.playlistStrategies.PickVideosStrategy;

public class TopRankedStrategy implements PickVideosStrategy{

	@Override
	public List<Video> pickVideos(int nVideos, List<Video> videos) {
		List<Video> result = new ArrayList<>();

		for(int i = 0; i < nVideos; i++) {
			Optional<Video> v = videos.stream().max(new Comparator<Video>() {
				@Override
				public int compare(Video v1, Video v2) {
					if (v1.getClassificacao() > v2.getClassificacao())
						return 1;
					else if(v1.getClassificacao() < v2.getClassificacao())
						return -1;
					else 
						return 0;
				}
			});

			if(v.isPresent()) {
				result.add(v.get());
				videos.remove(v.get());
				// se o max devolveu um Optional.empty, eh porque a stream nao tinha mais videos
				// logo nao vale a pena continuar a procura
			} else {
				return result;
			}
		}
		return result;
	}
}
