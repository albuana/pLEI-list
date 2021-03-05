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
import pleilist.app.facade.dto.Pair;
import pleilist.app.facade.dto.Playlist;
import pleilist.app.facade.handlers.AdicionarVideoHandler;
import pleilist.app.facade.handlers.CriarPlayListHandler;
import pleilist.app.facade.handlers.RegistarUtilizadorHandler;
import pleilist.app.facade.handlers.VerPlaylistHandler;

public class ClienteExemplo {
    public static void main(String[] args) {
        PleiList p = new PleiList();

        RegistarUtilizadorHandler regHandler = p.getRegistarUtilizadorHandler();

        try {
            regHandler.registarUtilizador("Felismina", "hortadafcul");
            regHandler.registarCurador("J", "bardoc2");
            regHandler.registarCurador("Silvino", "bardoc2");
            regHandler.registarCurador("Maribel", "torredotombo");
        } catch (UsernameInvalidoException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Optional<Sessao> talvezSessao = p.autenticar("Silvino", "bardoc2");

        talvezSessao.ifPresent( (Sessao s) -> {
            AdicionarVideoHandler avh;
            try {
                avh = p.getAdicionarVideoHandler(s);
                avh.iniciarAdicionar();
                avh.definirComoClip(true); // Vamos dizer que é um clip.
                try {
                    avh.indicaVideo("OOP in 7 Minutes", "https://www.youtube.com/watch?v=pTB0EiLXUC8");
                } catch (EnderecoInvalidoException e) {
                    e.printStackTrace();
                }
                avh.indicaDuracao(Duration.ofSeconds(7 * 60 + 33));

                for (String tag : "oop objects polymorphism".split(" ")) {
                    avh.indicaTag(tag);
                }

                String codigo = avh.defineComoPublico(false);
                System.out.println("O Silvino criou o video " + codigo);
            } catch (NaoEhCuradorException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Optional<Sessao> talvezOutraSessao = p.autenticar("Maribel", "torredotombo");
        talvezOutraSessao.ifPresent( (Sessao s) -> {
            AdicionarVideoHandler avh;
            try {
                avh = p.getAdicionarVideoHandler(s);
                avh.iniciarAdicionar();
                avh.definirComoClip(false); // Vamos dizer que é um stream.
                try {
                    avh.indicaVideo("RTP1", "https://www.rtp.pt/play/direto/rtp1");
                } catch (EnderecoInvalidoException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // Não indica duração! É um Stream!

                for (String tag : "portugues actualidade noticias praca_da_alegria".split(" ")) {
                    avh.indicaTag(tag);
                }

                String codigo = avh.defineComoPublico(true);
                System.out.println("A Maribel criou o video " + codigo);
            } catch (NaoEhCuradorException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        talvezSessao.ifPresent( (Sessao s) -> {
            CriarPlayListHandler cph;
            try {
                cph = p.getCriarPlayListHandler(s);
                cph.iniciaCriacao("Playlist de Domingo");

                for (String tag : "oop portugues".split(" ")) {
                    List<Entrada> entradasActuaisDaPlaylist = cph.obterEntradasActuais();
                    System.out.println("..........");
                    entradasActuaisDaPlaylist.stream().forEach(System.out::println);
                    System.out.println("..........");

                    List<Pair<String, String>> videos = cph.obterVideosComHashtag(tag);

                    boolean eStream;
                    try {
                        eStream = cph.indicarCodigo(videos.get(0).getSecond());

                        if (eStream) {
                            cph.indicaDuracao(Duration.ofMinutes(30));
                        }
                    } catch (CodigoNaoConhecidoException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            } catch (NaoEhCuradorException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Optional<Sessao> talvezSessaoUtilizador = p.autenticar("Felismina", "hortadafcul");

        talvezSessaoUtilizador.ifPresent( (Sessao s) -> {

            VerPlaylistHandler vph = p.getVerPlaylistHandler(s);

            List<Playlist> resultados = vph.procurarPorTag("oop");

            try {
                Duration duracaoTotal = vph.escolhePlaylist(resultados.get(0).getCodigo());
            } catch (CodigoNaoConhecidoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Pair<String, Duration> video = vph.verProximo();

            try {
                vph.classificar(3);
            } catch (ClassificacaoInvalidaExeption e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        });

    }
}
