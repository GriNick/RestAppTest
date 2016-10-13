package com.grinick.entity;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.grinick.serializers.JsonTimeDeserializer;
import com.grinick.serializers.JsonTimeSerializer;

public class BookingRequestAccepted {
	Date startDateTime;
	Date endDateTime;
	String requester;
	@JsonSerialize(using = JsonTimeSerializer.class)
	public Date getStartDateTime() {
		return startDateTime;
	}
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public void setStartDateTime(Date dateBegin) {
		this.startDateTime = dateBegin;
	}
	@JsonSerialize(using = JsonTimeSerializer.class)
	public Date getEndDateTime() {
		return endDateTime;
	}
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public void setEndDateTime(Date dateEnd) {
		this.endDateTime = dateEnd;
	}
	public String getRequester() {
		return requester;
	}
	public void setRequester(String requester) {
		this.requester = requester;
	}
	public String toString() {
		return "[ requester: " + this.requester +" , startDate: "+this.startDateTime+" , endDateTime: "+this.endDateTime+" ]";
	}
}
