package eng.la.util.va.csrf;
//engsecurity VA
import java.security.MessageDigest;
import java.security.SecureRandom;

import java.util.Date;

public class GenerateCSRFToken {
    
    /**
     * Singleton instance.
     */
    static private GenerateCSRFToken instance = null;
    
    protected SecureRandom rng = null;
    
    protected GenerateCSRFToken() throws Exception {
        
        //Initialize SecureRandom
        rng = SecureRandom.getInstance("SHA1PRNG");
        
        // Viene utilizzato come seme la data attuale in millisecondi ottenendo un seme diverso ogni volta
        long seed = Math.abs((new Date().getTime()) + this.hashCode());
        rng.setSeed(seed);
    }
    
    /** Crea se necessario il generatore di token CSRF
     * @return una istanza del generatore di token CSRF.
     */
     static public synchronized GenerateCSRFToken instance() throws Exception {
        if(instance == null) {
          instance = new GenerateCSRFToken();
        }
        return instance;
     }
    
    
    /* Genera il token CSRF */
    public synchronized String generate() throws Exception {
              
              //generate a random number
              String randomNum = new Integer( rng.nextInt() ).toString();

              //get its digest
              MessageDigest sha = MessageDigest.getInstance("SHA-1");
              byte[] result =  sha.digest( randomNum.getBytes() );
              
            return hexEncode(result);
    }
    
    /**
    * Converte l'array di byte ritornati dal MessageDigest in una 
    * piu leggibile stringa di caratteri esadecimali.
    *
    * by David Flanagan's.
    * Another popular alternative is to use a "Base64" encoding.
    */
    static private String hexEncode( byte[] aInput){
      StringBuilder result = new StringBuilder();
      char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
      for ( int idx = 0; idx < aInput.length; ++idx) {
        byte b = aInput[idx];
        result.append( digits[ (b&0xf0) >> 4 ] );
        result.append( digits[ b&0x0f] );
      }
      return result.toString();
    }
}