package com.grinick.apps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.grinick.entity.BookingBatchRequest;
import com.grinick.entity.BookingDayAccepted;
import com.grinick.entity.BookingResponse;


@Path("/api")
public class RestService {	
	@POST
	@Path("/booking")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBookingCalendar(BookingBatchRequest batchRequest) {
		BookingResponse result = null;
		try {
			Controller ctrl = new Controller();
			System.out.println(batchRequest.getStartWorkingDay());
			System.out.println(batchRequest.getEndWorkingDay());
			ArrayList<BookingDayAccepted> calendar = ctrl.getBookingCalendar(batchRequest);	
			result = BookingAcceptedFactory.newBookingResponseInstance(200, "Successful", calendar);
		} catch (Exception e) {
			result = BookingAcceptedFactory.newBookingResponseInstance(500, "Incorrect parameters in the request", null);
		}
		return Response.ok().entity(result).build();		
	}

}

