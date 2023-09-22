package com.luxfacta.vernyy.api.shared;

import java.util.Locale;

import it.burning.cron.CronExpressionDescriptor;
import it.burning.cron.CronExpressionParser.Options;

public class CronUtils {

	public static String toText(String cronInicio, String cronFim) {
		
		String expressao = CronExpressionDescriptor.getDescription(cronInicio,new Options(){{
		       setLocale(new Locale("pt","BR"));
		       setUse24HourTimeFormat(true);
		}}).substring(2);
		
		String horaFim = String.format("%02d",Integer.parseInt(cronFim.split(" ")[1]));
		String minutoFim = String.format("%02d",Integer.parseInt(cronFim.split(" ")[0]));;
		
		expressao = expressao.replace(", e ", " e ");
		expressao = expressao.replace("somente de ", "");
		if (expressao.indexOf(",") > 0) 
			expressao = expressao.replaceFirst(",", " até " + horaFim + ":" + minutoFim);
		else 
			expressao = expressao + " até " + horaFim + ":" + minutoFim + " todos os dias";
			
		expressao = "De " + expressao;

		return expressao;
		
	}
	
}
