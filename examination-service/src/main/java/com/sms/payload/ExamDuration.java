package com.sms.payload;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class ExamDuration {
	
	

	    @NotNull
	    @Max(23)
	    private Integer hours;
	    
	    @NotNull
	    @Max(23)
	    private Integer minutes;
	    

	    public Integer getMinutes() {
			return minutes;
		}

		public void setMinutes(Integer minutes) {
			this.minutes = minutes;
		}

		public void setHours(Integer hours) {
			this.hours = hours;
		}

		public int getHours() {
	        return hours;
	    }

	    public void setHours(int hours) {
	        this.hours = hours;
	    }

}
