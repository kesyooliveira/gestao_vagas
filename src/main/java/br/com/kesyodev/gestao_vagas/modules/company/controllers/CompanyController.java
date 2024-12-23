package br.com.kesyodev.gestao_vagas.modules.company.controllers;

import br.com.kesyodev.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.kesyodev.gestao_vagas.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity){
//
//
//
//        int size = 200000; // Número de iterações
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
//

        try {
            var result =  this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
