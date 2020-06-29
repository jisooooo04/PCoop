package pcoop.backend.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler
	public String exceptinHandler(Exception e) {
		System.out.println("Exception handler: 예외 발생 ");
		e.printStackTrace();
		return "error";
	}
	
	@ExceptionHandler
	public String exceptinHandler(NumberFormatException nfe) {
		System.out.println("Exception handler: NumberFormat Exception 발생 ");
		return "error";
	}
	
	@ExceptionHandler
	public String exceptinHandler(NullPointerException nfe) {
		System.out.println("Exception handler: NullPointerException 예외 발생 ");
		return "error";
	}
}

