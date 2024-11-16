package br.com.kesyodev.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException{
    public UserFoundException(){
        super("Usuário já existente com esse [username] ou [email]");
    }
}
