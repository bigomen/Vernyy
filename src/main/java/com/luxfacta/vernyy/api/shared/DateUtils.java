package com.luxfacta.vernyy.api.shared;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	

	public static boolean isEqual(Date dt1, Date dt2) {
		if (dt1 == null || dt2 == null)
			return false;
		
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(dt1);

			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(dt2);
			
			if (
					cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) &&
					cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
					cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
					)
				return true;
			
			return false;

	}
	
}
