pLEI list - Meta4
=================

**Deadline:** 28 de Maio de 2019, 23:55, Hora de Lisboa

**Equipa:** O trabalho deve ser feito em grupos de 3. Excepcionalmente serão aceites grupos de 2.

**Dúvidas:** Dúvidas sobre o trabalho, bem como outras discussões como procura de elementos de grupo deverão ser feitas [neste endereço](https://git.alunos.di.fc.ul.pt/dco0001/dco_plei_list/issues).

**Instruções:** No repositório local da meta 3 deverá executar os seguintes comandos para juntar o código fornecido à sua cópia local:

```
git pull enunciado master
```


**Para entregar:** Deverá preencher o ficheiro autores.txt. O Código deverá ficar na mesma pasta onde se encontra. Façam os commits que acharem necessários. Depois, basta criar uma nova tag git e colocá-la no servidor:

```
git tag meta4
git push origin meta4
```

Não cumprir estas instruções levará a que o projecto nem seja corrigido, resultando numa nota de 0.

Fraude
------

Como futuro profissional, espera-se de si uma atitude irrepreensível,
em termos éticos e deontológicos. Tenha pois o maior cuidado em
respeitar e fazer respeitar a lei da criminalidade informática.

A nível académico, alunos detetados em situação de fraude ou plágio
(plagiadores e plagiados) em alguma prova ficam reprovados à
disciplina e serão alvo de processo disciplinar, o que levará a um
registo dessa incidência no processo de aluno, podendo conduzir à
suspensão letiva ou abandono da Universidade.

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



