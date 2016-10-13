package com.grinick.apps;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.grinick.entity.BookingRequestAccepted;
import com.grinick.exceptions.StopSearchingPlaceException;
import com.grinick.entity.BookingBatchRequest;
import com.grinick.entity.BookingDayAccepted;
import com.grinick.entity.BookingRequest;

public class Controller {
	public static final long HOUR = 3600*1000;
	
	//initial rearranging and some request validation
	public  List<BookingRequest> getOrderedBookingList(BookingBatchRequest batchRequest) {
		List<BookingRequest> requestList = batchRequest.getRequest();
		ArrayList<BookingRequest> orderedRequestList = new ArrayList<>();
		Iterator<BookingRequest> rlIt= requestList.iterator();
		while (rlIt.hasNext()) {
			try {
				BookingRequest request = rlIt.next();
				if (request.getSubmissionDateTime() == null || request.getRequestedDateTime() == null) {
					throw new StopSearchingPlaceException("Incorrect date/time format in booking request. Skipped");			
				}
				if (!request.isDatesValid(batchRequest.getStartWorkingDay(), batchRequest.getEndWorkingDay()))  {
					throw new StopSearchingPlaceException("Booking request not in bound of working day. Skipped");
				}
				for(int i = 0; i < orderedRequestList.size(); i++) {
					if (orderedRequestList.get(i).getSubmissionDateTime().after(request.getSubmissionDateTime()) ) {
						orderedRequestList.add(i,request);		
						throw new StopSearchingPlaceException("It's ok. Element -"+ request +" has been put to his place in initial queue of requests.");
					}
				}	
				orderedRequestList.add(request);
				throw new StopSearchingPlaceException("It's ok. Element -"+ request +" has been put to his place in initial queue of requests");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return orderedRequestList;
	}
    // main method generation response list 
	public  ArrayList<BookingDayAccepted> getBookingCalendar(BookingBatchRequest batchRequest) throws RuntimeException {
		ArrayList<BookingDayAccepted> calendar = new ArrayList<>();
		Date startDay = batchRequest.getStartWorkingDay();
		Date endDay = batchRequest.getEndWorkingDay(); 
		if (startDay== null	|| endDay  == null || startDay.after(endDay) ) throw new RuntimeException();	
		//initial reordering according to sumbission date
		List<BookingRequest> bookingOrderedList = getOrderedBookingList(batchRequest);		
		Iterator<BookingRequest> baIt = bookingOrderedList.iterator();
		List<BookingRequestAccepted> bookingAcceptedList = null;
		// going through all the valid requests and put them on their place in response 
		while (baIt.hasNext()) {
			try {
			BookingRequest req = baIt.next();	
			    // finding the correct place in booking accepted list
				for (int i = 0; i < calendar.size(); i++) {
					// new this date has been already placed to list
					if (calendar.get(i).getAcceptedDay().equals(req.getRequestedDay())) {
						bookingAcceptedList = calendar.get(i).getBookingAcceptedList();
						//finding the correct place in inner list of given requested date
						for (int j = 0; j < bookingAcceptedList.size(); j++) {							
						  if (req.isDatesOverlapped(bookingAcceptedList.get(j).getStartDateTime(), bookingAcceptedList.get(j).getEndDateTime())) {
							  throw new StopSearchingPlaceException("Booking request - " + req + " is overlaped with already accepted request -" + bookingAcceptedList.get(j) + " and cannot be accepted");
						  } else if (bookingAcceptedList.get(j).getStartDateTime().after(req.getEndDateTime()) || bookingAcceptedList.get(j).getStartDateTime().equals(req.getEndDateTime())  ) {
							        bookingAcceptedList.add(j, BookingAcceptedFactory.newBookingRequestAcceptedInstance(req.getRequestedDateTime(), req.getEndDateTime(), req.getRequester()));
							        throw new StopSearchingPlaceException("It's ok. Element has been put to his place.");
						  } 
						}
						bookingAcceptedList.add(BookingAcceptedFactory.newBookingRequestAcceptedInstance(req.getRequestedDateTime(), req.getEndDateTime(), req.getRequester()));
						throw new StopSearchingPlaceException("It's ok. Element -"+ req +" has been put to his place.");
					} 
					//new requested date
					else if (calendar.get(i).getAcceptedDay().after(req.getRequestedDay())) {
								calendar.add(i, BookingAcceptedFactory.newBookingDayAcceptedInstance(req.getRequestedDay(), req.getRequestedDateTime(), req.getEndDateTime(), req.getRequester()));
								throw new StopSearchingPlaceException("It's ok. Element -"+ req +" has been put to his place in accepted request list.");
							}
				}
				// first element is going to be added
				calendar.add(BookingAcceptedFactory.newBookingDayAcceptedInstance(req.getRequestedDay(), req.getRequestedDateTime(), req.getEndDateTime(), req.getRequester()));
				throw new StopSearchingPlaceException("It's ok. Element -"+ req +" has been put to his place in accepted request list.");
			} catch (StopSearchingPlaceException e) {
				System.out.println(e.getMessage());
			}				
		}		
		return calendar;
	}
}
