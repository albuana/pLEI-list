package pleilist.main;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import pleilist.app.PleiList;
import pleilist.app.excepcoes.ClassificacaoInvalidaExeption;
import pleilist.app.excepcoes.CodigoNaoConhecidoException;
import pleilist.app.excepcoes.EnderecoInvalidoException;
import pleilist.app.excepcoes.NaoEhCuradorException;
import pleilist.app.excepcoes.UsernameInvalidoException;
import pleilist.app.facade.Sessao;
import pleilist.app.facade.dto.Entrada;
import pleilist.app.facade.handlers.AdicionarVideoHandler;
import pleilist.app.facade.handlers.CriarPlayListHandler;
import pleilist.app.facade.handlers.RegistarUtilizadorHandler;
import pleilist.app.facade.handlers.VerPlaylistHandler;
import pleilist.app.facade.dto.Pair;
import pleilist.app.facade.dto.Playlist;

public class Cliente1 {
    public static void main(String[] args) {
        PleiList p = new PleiList();

        RegistarUtilizadorHandler regHandler = p.getRegistarUtilizadorHandler();
        //        regHandler.registarUtilizador("Ines", "123");
        try {
            regHandler.registarCurador("Ana", "abc");
            //        regHandler.registarCurador("Ana", "12s");
            regHandler.registarUtilizador("Daniel", "oi");
            regHandler.registarUtilizador("francisca001", "passe");
            regHandler.registarCurador("Ines", "123");
        } catch (UsernameInvalidoException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Optional<Sessao> talvezSessao = p.autenticar("Ines", "123");

        talvezSessao.ifPresent( (Sessao s) -> {

            //TESTE UC1
            AdicionarVideoHandler adh;
            try {
                adh = p.getAdicionarVideoHandler(s);
                adh.iniciarAdicionar();
                adh.definirComoClip(true);
                try {
                    adh.indicaVideo("1o Video", "you.pt");
                } catch (EnderecoInvalidoException e) {
                    e.printStackTrace();
                }
                adh.indicaDuracao(Duration.ofSeconds(3 * 60 + 15));
                adh.indicaTag("first");
                adh.indicaTag("best");
                adh.indicaTag("hello_world");
                System.out.println("Video criado com id: " + adh.defineComoPublico(true));

                adh.iniciarAdicionar();
                adh.definirComoClip(false);
                try {
                    adh.indicaVideo("1o Stream", "rtp.pt");
                } catch (EnderecoInvalidoException e) {
                    e.printStackTrace();
                }
                adh.indicaTag("stream");
                adh.indicaTag("always");
                adh.indicaTag("hello_world");
                System.out.println("Video criado com id: " + adh.defineComoPublico(false));

                adh.definirComoClip(false);
                try {
                    adh.indicaVideo("2o Stream", "sic.pt");
                } catch (EnderecoInvalidoException e) {
                    e.printStackTrace();
                }
                adh.indicaTag("stream");
                adh.indicaTag("always");
                adh.indicaTag("doneThisBefore");
                System.out.println("Video criado com id: " + adh.defineComoPublico(true));

                adh.definirComoClip(true);
                try {
                    adh.indicaVideo("Lullaby", "music.pt");
                } catch (EnderecoInvalidoException e) {
                    e.printStackTrace();
                }
                adh.indicaDuracao(Duration.ofSeconds(3 * 60));
                adh.indicaTag("music");
                adh.indicaTag("best");
                adh.indicaTag("doneThisBefore");
                System.out.println("Video criado com id: " + adh.defineComoPublico(true));
            } catch (NaoEhCuradorException e) {
                e.printStackTrace();
            }
            //TESTE UC2
            CriarPlayListHandler cph;
            try {
                cph = p.getCriarPlayListHandler(s);
                cph.iniciaCriacao("1a_playlist");

                List<Entrada> entradasAtuais = cph.obterEntradasActuais();
                entradasAtuais.stream().forEach(System.out::println);
                List<Pair<String, String>> videosTag = cph.obterVideosComHashtag("naoExiste");
                videosTag.stream().forEach(System.out::println);

                boolean ehStream;
                try {
                    ehStream = cph.indicarCodigo(videosTag.get(0).getSecond());
                    System.out.println(ehStream);
                } catch (CodigoNaoConhecidoException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                cph.indicaDuracao(Duration.ofSeconds(60 * 5));

                entradasAtuais = cph.obterEntradasActuais();
                entradasAtuais.stream().forEach(System.out::println);

                try {
                    ehStream = cph.indicarCodigo(videosTag.get(1).getSecond());
                    System.out.println(ehStream);
                } catch (CodigoNaoConhecidoException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                entradasAtuais = cph.obterEntradasActuais();
                entradasAtuais.stream().forEach(System.out::println);

                try {
                    ehStream = cph.indicarCodigo(videosTag.get(3).getSecond());
                    System.out.println(ehStream);
                } catch (CodigoNaoConhecidoException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                entradasAtuais = cph.obterEntradasActuais();
                entradasAtuais.stream().forEach(System.out::println);
            } catch (NaoEhCuradorException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });   

        talvezSessao = p.autenticar("francisca001", "passe");

        talvezSessao.ifPresent( (Sessao s) -> {
            //TESTE UC4
            VerPlaylistHandler vph = p.getVerPlaylistHandler(s);
            List<Playlist> playlistsTag = vph.procurarPorTag("best");

            for(Playlist pl : playlistsTag) {
                System.out.println("NOME:" + pl.getNome() + "   CODIGO: " + pl.getCodigo() + "   PONT: " + pl.getPontuacao());
            }

            try {
                System.out.println(vph.escolhePlaylist(playlistsTag.get(0).getCodigo()));

                for(int i = 0; i < 3; i++) {
                    Pair<String, Duration> par = vph.verProximo();
                    if(!par.equals(null)) {
                        System.out.println("ENDERECO: " + par.getFirst() + "   DURACAO: " + par.getSecond());
                        vph.classificar(5);                    
                    }
                }

                VerPlaylistHandler vph2 = p.getVerPlaylistHandler(s);
                playlistsTag = vph2.procurarPorTag("best");
                for(Playlist pl : playlistsTag) {
                    System.out.println("NOME:" + pl.getNome() + "   CODIGO: " + pl.getCodigo() + "   PONT: " + pl.getPontuacao());
                }

                System.out.println(vph2.escolhePlaylist(playlistsTag.get(0).getCodigo()));
                Pair<String, Duration> par = vph2.verProximo();
                System.out.println("ENDERECO: " + par.getFirst() + "   DURACAO: " + par.getSecond());
                vph2.classificar(4); 
                par = vph2.verProximo();
                System.out.println("ENDERECO: " + par.getFirst() + "   DURACAO: " + par.getSecond());
                vph2.classificar(4); 

                playlistsTag = vph2.procurarPorTag("best");
                for(Playlist pl : playlistsTag) {
                    System.out.println("NOME:" + pl.getNome() + "   CODIGO: " + pl.getCodigo() + "   PONT: " + pl.getPontuacao());
                }  
            } catch (CodigoNaoConhecidoException | ClassificacaoInvalidaExeption e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
}
