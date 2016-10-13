package com.grinick.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.grinick.serializers.JsonDateTimeDeserializer;
import com.grinick.serializers.JsonDateTimeSerializer;
import com.grinick.serializers.JsonDateTimeExDeserializer;
import com.grinick.serializers.JsonDateTimeExSerializer;

public class BookingRequest {
	private String requester;
	private Date submissionDateTime;
	private Date requestedDateTime;
	private float duration;
	
	private boolean isFirstTimeFollowedBySecond(Date first, Date second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(first);
		int firstHours = cal.get(Calendar.HOUR_OF_DAY);
		int firstMinutes = cal.get(Calendar.MINUTE);
		int firstSeconds = cal.get(Calendar.SECOND);
		cal.setTime(second);
		int secondHours = cal.get(Calendar.HOUR_OF_DAY);
		int secondMinutes = cal.get(Calendar.MINUTE);
		int secondSeconds = cal.get(Calendar.SECOND);
		if (secondHours > firstHours) return true;
		if (secondHours == firstHours && secondMinutes > firstMinutes) return true;
		if (secondHours == firstHours && secondMinutes == firstMinutes && secondSeconds >= firstSeconds) return true;
		return false;

	}
	public static final long HOUR = 3600*1000;
	public String getRequester() {
		return requester;
	}
	public void setRequester(String requester) {
		this.requester = requester;
	}
	@JsonSerialize(using = JsonDateTimeExSerializer.class)
	public Date getSubmissionDateTime() {
		return submissionDateTime;
	}
	@JsonDeserialize(using = JsonDateTimeExDeserializer.class)
	public void setSubmissionDateTime(Date submissionDateTime) {
		this.submissionDateTime = submissionDateTime;
	}
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getRequestedDateTime() {
		return requestedDateTime;
	}
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	public void setRequestedDateTime(Date requestedDateTime) {
		this.requestedDateTime = requestedDateTime;
	}
	public float getDuration() {
		return duration;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}	
	
	public Date getRequestedDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(requestedDateTime);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	public Date getTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(requestedDateTime);
		cal.set(Calendar.YEAR, 0);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();		
	}
	
	public Date getEndDateTime() {
		return new Date(this.requestedDateTime.getTime()+ (int)(HOUR*this.duration));
	}
	public boolean isDatesValid(Date startWork, Date endWork) {		
		Date reqStartTime = this.getRequestedDateTime();
		Date reqEndTime = this.getEndDateTime();
		boolean result = this.requestedDateTime != null && this.submissionDateTime !=null; 
		result = result && isFirstTimeFollowedBySecond(startWork, this.requestedDateTime);
		result = result && isFirstTimeFollowedBySecond(this.getEndDateTime(), endWork); 
		return result;
	}
	
	public boolean isDatesOverlapped(Date start, Date end) {
		return  (   (start.before(this.requestedDateTime) || start.equals(this.requestedDateTime) ) && end.after(this.requestedDateTime) )
	             || ( (this.requestedDateTime.before(start) || this.requestedDateTime.equals(start) ) && this.getEndDateTime().after(start) 
	            );
	}
	public String toString() {
		return "[ requester: " + this.requester +" , submissionDateTime: "+this.submissionDateTime + " , startDate: "+this.requestedDateTime+" , duration: "+this.duration+" ]";
	}
}
