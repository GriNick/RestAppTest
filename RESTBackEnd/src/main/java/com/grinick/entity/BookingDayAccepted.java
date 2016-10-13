package com.grinick.entity;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.grinick.serializers.JsonDateDeserializer;
import com.grinick.serializers.JsonDateSerializer;

public class BookingDayAccepted {
	Date day;
	List<BookingRequestAccepted> bookingAcceptedList;
	
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getAcceptedDay() {
		return day;
	}
	@JsonDeserialize(using = JsonDateDeserializer.class)
	public void setAcceptedDay(Date date) {
		this.day = date;
	}
	public List<BookingRequestAccepted> getBookingAcceptedList() {
		return bookingAcceptedList;
	}
	public void setBookingAcceptedList(List<BookingRequestAccepted> bookingAcceptedList) {
		this.bookingAcceptedList = bookingAcceptedList;
	}
}
