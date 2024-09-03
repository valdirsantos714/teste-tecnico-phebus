package com.valdirsantos714.communitycenter.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TratadorDeErros {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity trataErro400(MethodArgumentNotValidException e) {
        var erros = e.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());

    }

    //Record para capturar somente o campo de mensagem e o campo que faltou ser preenchido corretamente
    private record DadosErrosValidacao(String campo, String mensagem){
        public DadosErrosValidacao(FieldError e) {
            this(e.getField(), e.getDefaultMessage());
        }
    }
}
