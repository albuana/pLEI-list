package pleilist.main;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import pleilist.app.PleiList;
import pleilist.app.excepcoes.ClassificacaoInvalidaExeption;
import pleilist.app.excepcoes.CodigoNaoConhecidoException;
import pleilist.app.excepcoes.EnderecoInvalidoException;
import pleilist.app.excepcoes.EstrategiaInvalidaException;
import pleilist.app.excepcoes.NaoEhCuradorException;
import pleilist.app.excepcoes.UsernameInvalidoException;
import pleilist.app.facade.Sessao;
import pleilist.app.facade.dto.Pair;
import pleilist.app.facade.dto.Playlist;
import pleilist.app.facade.handlers.AdicionarVideoHandler;
import pleilist.app.facade.handlers.CriarPlaylistAutomaticamenteHandler;
import pleilist.app.facade.handlers.RegistarUtilizadorHandler;
import pleilist.app.facade.handlers.VerPlaylistHandler;

public class Cliente2 {
	public static void main(String[] args) {
		PleiList p = new PleiList();

		RegistarUtilizadorHandler regHandler = p.getRegistarUtilizadorHandler();
		try {
			regHandler.registarCurador("Ines", "123");
		} catch (UsernameInvalidoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Optional<Sessao> talvezSessao = p.autenticar("Ines", "123");

		talvezSessao.ifPresent( (Sessao s) -> {

			// TESTE UC1
			AdicionarVideoHandler adh;
			try {
				// 1o Video
				adh = p.getAdicionarVideoHandler(s);
				adh.iniciarAdicionar();
				adh.definirComoClip(true);
				adh.indicaVideo("Ed Sheeran & Justin Bieber - I Don't Care",
						"https://www.youtube.com/watch?v=y83x7MgzWOA");
				adh.indicaDuracao(Duration.ofSeconds(3 * 60 + 42));
				adh.indicaTag("music");
				adh.indicaTag("Ed Sheeran");
				adh.indicaTag("Justin Bieber");
				System.out.println("Video criado com id: " + adh.defineComoPublico(true));

				// 2o Video
				adh = p.getAdicionarVideoHandler(s);
				adh.iniciarAdicionar();
				adh.definirComoClip(true);
				adh.indicaVideo("Ed Sheeran - Happier",
						"https://www.youtube.com/watch?v=iWZmdoY1aTE");
				adh.indicaDuracao(Duration.ofSeconds(3 * 60 + 35));
				adh.indicaTag("music");
				adh.indicaTag("Ed Sheeran");
				adh.indicaTag("sad");
				System.out.println("Video criado com id: " + adh.defineComoPublico(true));

				// 3o Video
				adh = p.getAdicionarVideoHandler(s);
				adh.iniciarAdicionar();
				adh.definirComoClip(true);
				adh.indicaVideo("Ed Sheeran - Photograph",
						"https://www.youtube.com/watch?v=nSDgHBxUbVQ");
				adh.indicaDuracao(Duration.ofSeconds(4 * 60 + 34));
				adh.indicaTag("music");
				adh.indicaTag("Ed Sheeran");
				adh.indicaTag("babys");
				System.out.println("Video criado com id: " + adh.defineComoPublico(true));

				// 4o Video
				adh = p.getAdicionarVideoHandler(s);
				adh.iniciarAdicionar();
				adh.definirComoClip(true);
				adh.indicaVideo("Ed Sheeran - Perfect",
						"https://www.youtube.com/watch?v=iKzRIweSBLA");
				adh.indicaDuracao(Duration.ofSeconds(4 * 60 + 23));
				adh.indicaTag("music");
				adh.indicaTag("Ed Sheeran");
				adh.indicaTag("love");
				System.out.println("Video criado com id: " + adh.defineComoPublico(true));

				// 5o Video
				adh = p.getAdicionarVideoHandler(s);
				adh.iniciarAdicionar();
				adh.definirComoClip(true);
				adh.indicaVideo("Ed Sheeran - Dive",
						"https://www.youtube.com/watch?v=qFCiLrY0T5w");
				adh.indicaDuracao(Duration.ofSeconds(3 * 60 + 58));
				adh.indicaTag("music");
				adh.indicaTag("Ed Sheeran");
				adh.indicaTag("Beyonce");
				System.out.println("Video criado com id: " + adh.defineComoPublico(true));
			} catch (NaoEhCuradorException | EnderecoInvalidoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};

			// TESTE UC3.1
			CriarPlaylistAutomaticamenteHandler cpah = p.getCriarPlaylistAutomaticamenteHandler(s);
			System.out.println(cpah.iniciaCriacao("ed sheeran"));
			try {
				System.out.println(cpah.indicaDadosPaylist(
						"pleilist.app.playlistStrategies.strategies.RandomVideosStrategy", 3));
			} catch (EstrategiaInvalidaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// TESTE UC4
			VerPlaylistHandler vph = p.getVerPlaylistHandler(s);
			List<Playlist> playlistsTag = vph.procurarPorTag("Ed Sheeran");

			for(Playlist pl : playlistsTag) {
				System.out.println("NOME:" + pl.getNome() + "   CODIGO: " + pl.getCodigo() + "   PONT: " + pl.getPontuacao());
			}

			try {
				System.out.println(vph.escolhePlaylist(playlistsTag.get(0).getCodigo()));
			} catch (CodigoNaoConhecidoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for(int i = 0; i < 3; i++) {
				Pair<String, Duration> par = vph.verProximo();
				if(!par.equals(null)) {
					System.out.println("ENDERECO: " + par.getFirst() + "   DURACAO: " + par.getSecond());
					try {
						vph.classificar(5);
					} catch (ClassificacaoInvalidaExeption e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(i == 2) {
						try {
							vph.classificar(4);
						} catch (ClassificacaoInvalidaExeption e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			playlistsTag = vph.procurarPorTag("Ed Sheeran");
			for(Playlist pl : playlistsTag) {
				System.out.println("NOME:" + pl.getNome() + "   CODIGO: " + pl.getCodigo() + "   PONT: " + pl.getPontuacao());
			}


			// TESTE UC3.2
			cpah = p.getCriarPlaylistAutomaticamenteHandler(s);
			System.out.println(cpah.iniciaCriacao("ed sheeran - top"));
			try {
				System.out.println(cpah.indicaDadosPaylist(
						"pleilist.app.playlistStrategies.strategies.TopRankedStrategy", 2));
			} catch (EstrategiaInvalidaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			vph = p.getVerPlaylistHandler(s);
			playlistsTag = vph.procurarPorTag("Ed Sheeran");

			for(Playlist pl : playlistsTag) {
				System.out.println("NOME:" + pl.getNome() + "   CODIGO: " + pl.getCodigo() + "   PONT: " + pl.getPontuacao());
			}
		});

	}
}
