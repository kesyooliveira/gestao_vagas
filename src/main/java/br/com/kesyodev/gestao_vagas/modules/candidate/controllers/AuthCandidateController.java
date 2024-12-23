package br.com.kesyodev.gestao_vagas.modules.candidate.controllers;


import br.com.kesyodev.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.kesyodev.gestao_vagas.modules.candidate.useCases.AuthCanditateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    private AuthCanditateUseCase authCanditateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO){

//
//
//        int size = 300000; // Número de iterações
//        String[] largeArray = new String[size]; // Array para armazenar os objetos
//
//        // Loop que aloca uma string grande a cada iteração
//        for (int i = 0; i < size; i++) {
//            largeArray[i] = new String(new char[10000]); // String de 10.000 caracteres
//        }
//
//        // Imprimir um valor para evitar otimizações do compilador
//        System.out.println("Loop finalizado. Consumindo memória...");
//

        try {

            var token = this.authCanditateUseCase.execute(authCandidateRequestDTO);
            return ResponseEntity.ok().body(token);

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

    }


}
