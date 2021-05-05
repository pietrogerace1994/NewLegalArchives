*******************************************************************************

BuildReadMe.txt

Questo file fornisce informazioni per il build con Maven

*******************************************************************************

PRECONDIZIONI

	- JDK 1.8.0_77
	  * Impostare la variabile JAVA_HOME indicando il path \lib

	- MAVEN REPOSITORY 3.0.3
	  * Impostare la variabile MAVEN_HOME indicando il path dove e' installato 

	- LIBRERIA OJDBC14-10.2.0.3.0.JAR
	  * Disponibile nel workset al path \laws\lib\

	- PATH WINDOWS
	  * Impostare la variabile PATH nel modo seguente:
            set path=%java_home%\bin;%maven_home%\bin;c:\windows;c:\windows\system32;

	- Verificare inoltre l'esistenza della directory seguente:
          \laws\target\

BUILD

	- Portarsi nella directory \laws\
      
	-  Solo se si fa il build la prima volta, eseguire il seguente comando per installare la libreria ojdbc:
	  > mvn install:install-file -Dfile=<directory di lavoro>\laws\lib\ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=12.1.0.2 -Dpackaging=jar [INVIO]

	- Eseguire il seguente comando per il build:
          > mvn clean install

	- Verificare il risultato SUCCESS dell'intero riepilogo di build


TARGET

	- Di seguito e' riportata la lista dei file prodotti per il rilascio.
	  I suddetti file devono essere riportati in configurazione nella directory \laws\target\

          1) laws\target\la-lucystar-ear-1.0.0.ear