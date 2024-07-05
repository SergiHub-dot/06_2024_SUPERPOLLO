package com.sinensia.superpollo.presentation.config;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GestorCentralizadoExcepciones extends ResponseEntityExceptionHandler{

	    // ************************************************************************************************************
	
		@Override
		protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {	
			return handleExceptionInternal(ex, new ErrorHttpCustomizado(ex.getMessage()), headers, HttpStatus.BAD_REQUEST, request);
		}
		
		// ************************************************************************************************************

		@Override
		protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
				
			String tipoEntrante = ex.getValue().getClass().getSimpleName();
			String tipoRequerido = ex.getRequiredType().getSimpleName();
			
			String mensaje = "El valor " + ex.getValue() + " es de tipo [" + tipoEntrante + "] cuando se requeria un [" + tipoRequerido + "]";
			
			return handleExceptionInternal(ex, new ErrorHttpCustomizado(mensaje), headers, HttpStatus.BAD_REQUEST, request);
		}
		
		// ************************************************************************************************************
		
		@ExceptionHandler(Exception.class)
		protected ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request){
			
			String mensaje = "Se ha producido un error en el servidor";
		
			return handleExceptionInternal(ex, new ErrorHttpCustomizado(mensaje), null, HttpStatus.INTERNAL_SERVER_ERROR, request);
		}
		
		// ************************************************************************************************************
		
		@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
		protected ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request){
			return handleExceptionInternal(ex, new ErrorHttpCustomizado(ex.getMessage()), null, HttpStatus.BAD_REQUEST, request);
		}
		
		// ************************************************************************************************************
		
		@ExceptionHandler(PresentationException.class)
		protected ResponseEntity<Object> handlePresentationException(PresentationException ex, WebRequest request){
			return handleExceptionInternal(ex, new ErrorHttpCustomizado(ex.getMessage()), null, ex.getHttpStatus(), request);
		}
	
}
