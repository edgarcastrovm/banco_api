package com.banca.api.core.base;

import com.banca.api.core.utils.ResponseWrapper;
import com.banca.api.model.ClienteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Servicio base utilizado para todas las operaciones realizadas a la BDD.
 *
 * @param <S> tipo de clase de la cual se extiende.
 * @param <E> tipo de clase de las operaciones de BDD.
 */
public abstract class BaseService<S, E> {

    protected final Logger LOG;

    public BaseService(Class<S> clazz) {
        this.LOG = LoggerFactory.getLogger(clazz);
    }

    public ResponseEntity<?> buildResponse() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ResponseWrapper<E>> buildResponse(HttpStatus status) {
        return new ResponseEntity<>(
                new ResponseWrapper
                        .ResponseWrapperBuilder<E>()
                        .setCode("ER-00")
                        .setResult(null)
                        .build()
                , status);
    }
    public ResponseEntity<?> buildBadResponse(HttpStatus status) {
        return new ResponseEntity<>("Error interno", status);
    }

    public ResponseEntity<?> buildBadResponse(HttpStatus status,String message) {
        return new ResponseEntity<>(message, status);
    }

    public ResponseEntity<ResponseWrapper<E>> buildResponse(E result) {
        return new ResponseEntity<>(
                new ResponseWrapper
                        .ResponseWrapperBuilder<E>()
                        .setCode("EX-01")
                        .setResult(result)
                        .build()
                , HttpStatus.OK);
    }

    public ResponseEntity<ResponseWrapper<List<E>>> buildResponse(List<E> result) {
        return new ResponseEntity<>(
                new ResponseWrapper
                        .ResponseWrapperBuilder<List<E>>()
                        .setCode("EX-01")
                        .setResult(result)
                        .build(),
                HttpStatus.OK);
    }
}