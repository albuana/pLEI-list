package pleilist.app.playlistStrategies.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pleilist.app.dominio.Hashtag;
import pleilist.app.dominio.videos.Video;
import pleilist.app.playlistStrategies.PickVideosStrategy;

public class ChainedVideosStrategy implements PickVideosStrategy {


	//  deverá escolher até X vídeos, onde o primeiro vídeo é
	//	aleatório e cada novo vídeo é escolhido de forma aleatória, 
	//	desde que partilhe um hashtag com o anterior.


	@Override
	public List<Video> pickVideos(int nVideos, List<Video> videos) {
		if(nVideos == 0) return new ArrayList<>();

		// usamos Set para ter a certeza que nao mandamos videos duplicados
		List<Video> result = new ArrayList<>();
		Random r = new Random();

		// 1a iteracao eh feita fora do ciclo
		//vou buscar o video cujo indice eh escolhido ao calhas de todos os indices disponiveis
		Video v = videos.get(r.nextInt(videos.size()));
		// adiciono o video ao resultado
		result.add(v);
		
		// caso em que nao existe nenhum video com hashtag comum a v, eh suposto pararmos
		// exists apanha esse caso
		boolean exists = true;

		while(result.size() < nVideos && exists) {
			List<Hashtag> hashtags = v.getHashtags();
			
			exists = false;
			
			for(Hashtag  h : hashtags) {
				List<Video> videoss = h.getVideos();
				Video temp;
				if(videoss.size() > 1) {
					temp = videoss.get(0).equals(v) ? videoss.get(1) : videoss.get(0);
					result.add(temp);
					v = temp;
					
					exists = true;
					break;
				}
			}
		}
		return result;
	}
}
