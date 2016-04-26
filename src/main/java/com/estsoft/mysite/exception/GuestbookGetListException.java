package com.estsoft.mysite.exception;

@SuppressWarnings("Serial")
public class GuestbookGetListException extends Exception{
	
	public GuestbookGetListException(){
		super("Exception occurs: get guestbook list");
	}
	
	public GuestbookGetListException(String message){
		super(message);		
	}
}
