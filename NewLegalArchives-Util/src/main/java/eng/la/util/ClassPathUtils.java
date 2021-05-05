package eng.la.util;

import java.io.InputStream;
import java.net.URL;

/**
 * Classe di utility per la gestione delclasspath delle risorse.
 */
public final class ClassPathUtils {
  private ClassPathUtils(){}
  
  /**
   * Restuisce un riferimento del classpath alla risorsa passata attraverso il parametro name
   * @param clazz classe utilizzata per caricare la risorsa
   * @param name percorso della risorsa
   * @return URL recuperato attraverso il metodo getResource della classe clazz
   * @throws java.lang.RuntimeException nel caso di risorsa non trovata
   */
  public static URL getResource(Class<?> clazz, String name) {
    URL url = clazz.getResource(name);
    if(url == null){
      throw new RuntimeException("Cannot find classpath resource: [" + name + "]");
    }
    return url;
  }

  /**
   * Restituisce un riferimento di tipo java.io.InputStream per la risorsa passata in input.
   * @param obj oggetto utilizzato per caricare la risorsa
   * @param name percorso della risorsa
   * @return InputStream recuperato attraverso il metodo getResourceAsStream della classe obj.getClass()
   * @throws java.lang.RuntimeException nel caso di risorsa non trovata
   */
  public static InputStream getResourceAsStream(Object obj, String name){
    return getResourceAsStream(obj.getClass(), name);
  }

  /**
   * Restituisce un riferimento di tipo java.io.InputStream per la risorsa passata in input.
   * @param clazz classe utilizzata per caricare la risorsa
   * @param name percorso della risorsa
   * @return InputStream recuperato attraverso il metodo getResourceAsStream della classe clazz
   * @throws java.lang.RuntimeException nel caso di risorsa non trovata
   */
  public static InputStream getResourceAsStream(Class<?> clazz, String name){
    InputStream is = clazz.getResourceAsStream(name);
    if(is == null){
      throw new RuntimeException("Cannot find classpath resource: [" + name + "]");
    }
    return is;
  }
}