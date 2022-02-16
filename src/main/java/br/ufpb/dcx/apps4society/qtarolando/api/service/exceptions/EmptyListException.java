package br.ufpb.dcx.apps4society.qtarolando.api.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyListException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmptyListException(String msg) {
        super(msg);
    }

    public EmptyListException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
