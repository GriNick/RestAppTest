package com.grinick.exceptions;



public class StopSearchingPlaceException extends Exception {
  public StopSearchingPlaceException() {
	  super();
  }
  public StopSearchingPlaceException(String message) {
	  super(message);
  }
}
