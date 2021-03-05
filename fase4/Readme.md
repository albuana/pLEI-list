pLEI list - Meta4
=================

Objectivo
---------

A terceira meta consiste em re-organizar e completar as funcionalidades desenvolvidas, recorrendo a padrões de desenho para resolver alguns dos problemas.

1. Deverá tratar das extensões dos casos de uso, lançando excepções nos casos relevantes.

2. Deverá usar o padrão Singleton para garantir que os identificadores dos vídeos são únicos.

3. Deverá usar o padrão Adapter para permitir usar uma das duas classes fornecidas (com.chavetasfechadas.UrlChecker e net.padroesfactory.AddressVerifier) para verificar se um dado endereço existe.

4. Deverá usar o padrão Observer para que o atributo correspondente à pontuação de uma playlist seja actualizado sempre que a pontuação de um vídeo é alterada.

5. Deverá usar o padrão Strategy para implementar o UC3:



UC3 - Criar playlist automaticamente
------------------------------------------

Um utilizador pode pedir para ser gerada uma playlist automática.

	1. O utilizador pede ao sistema a lista de critérios a usar, indicando o nome que pretende que a playlist tenha.

	2. O sistema devolve ao utilizador a lista de estratégias que foram configuradas na aplicação (num ficheiro properties)
	
	3. O utilizador indica ao sistema qual a estratégia a usar, bem como o número de vídeos a incluir.
	
	4. O sistema retorna ao utilizador o código da playlist criada.

Deverá implementar as seguintes estratégias, bem como outras que entenda:

	* RandomVideos: deverá escolher vídeos aleatórios até satisfazer o número pretendido.

	* TopRanked: deverá escolher os X vídeos mais votados.
	
	* ChainedVideos: deverá escolher até X vídeos, onde o primeiro vídeo é aleatório e cada novo vídeo é escolhido de forma aleatória, desde que partilhe um hashtag com o anterior.



