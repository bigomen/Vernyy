package com.luxfacta.vernyy.api.shared;

import java.util.regex.Pattern;

public class SenhaUtils {
	
	public static boolean validaComplexidade(String senha) {
		Pattern ptUpperCase = Pattern.compile(".*[A-Z].*");
		Pattern ptLowerCase = Pattern.compile(".*[a-z].*");
		Pattern ptNumbers = Pattern.compile(".*\\d.*");

		boolean hasUpperCase = ptUpperCase.matcher(senha).matches();
		boolean hasLowerCase = ptLowerCase.matcher(senha).matches();
		boolean hasNumbers = ptNumbers.matcher(senha).matches();
		boolean hasLength = senha.length() > 7;

		return !(!hasLowerCase || !hasUpperCase || !hasNumbers || !hasLength);
	}
	

}
