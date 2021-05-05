package eng.la.util;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyValidator {

	public static boolean mailSyntaxCheck(String email) {
		
		if(email == null || email.equalsIgnoreCase("")){
			return false;
		}
		
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		boolean matchFound = m.matches();
		StringTokenizer st = new StringTokenizer(email, ".");
		String lastToken = null;
		while (st.hasMoreTokens()) {
			lastToken = st.nextToken();
		}

		if (matchFound && lastToken.length() >= 2
				&& email.length() - 1 != lastToken.length()) {
			return true;
		} else {
			return false;
		}
	}
}
