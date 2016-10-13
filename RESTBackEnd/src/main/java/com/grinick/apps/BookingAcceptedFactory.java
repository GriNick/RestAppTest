package com.grinick.apps;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.grinick.entity.BookingDayAccepted;
import com.grinick.entity.BookingRequestAccepted;
import com.grinick.entity.BookingResponse;

public class BookingAcceptedFactory {
	public static BookingRequestAccepted newBookingRequestAcceptedInstance(Date startDateTime, Date endDateTime, String requester) {
		BookingRequestAccepted instance = new BookingRequestAccepted();
		instance.setStartDateTime(startDateTime);
		instance.setEndDateTime(endDateTime);
		instance.setRequester(requester);
		return instance;		
	}
	
	public static BookingDayAccepted newBookingDayAcceptedInstance(Date day, Date startDateTime, Date endDateTime, String requester) {
		BookingDayAccepted instance = new BookingDayAccepted();
		BookingRequestAccepted accepted = newBookingRequestAcceptedInstance(startDateTime, endDateTime, requester);
		instance.setAcceptedDay(day);
		List<BookingRequestAccepted> list = new ArrayList<>();
		list.add(accepted);
		instance.setBookingAcceptedList(list);
		return instance;
	}
	
	public static BookingResponse newBookingResponseInstance(int statusCode, String errMessage, ArrayList<BookingDayAccepted> data) {
		BookingResponse result = new BookingResponse();
		result.setStatusCode(statusCode);
		result.setErrMessage(errMessage);
		result.setData(data);
		return result;
	}
}
