package br.com.kesyodev.gestao_vagas.modules.candidate.controllers;

import br.com.kesyodev.gestao_vagas.modules.candidate.CandidateEntity;

import br.com.kesyodev.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.kesyodev.gestao_vagas.modules.candidate.useCases.DeleteCandidateUseCase;
import br.com.kesyodev.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.kesyodev.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.kesyodev.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    DeleteCandidateUseCase deleteCandidateUseCase;

    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity){
        try{
            var result = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> get(HttpServletRequest request){

        var idCandidate = request.getAttribute("candidate_id");

        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Tag(name="Candidato", description = "Informações do candidato")
    @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Essa função é resposável por listar todas as vagas disponíveis, baseada no filtro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter){
        return this.listAllJobsByFilterUseCase.execute(filter);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<String> deleteCandidate(@RequestParam String username){
        try {
            this.deleteCandidateUseCase.execute(username);
            return ResponseEntity.ok("Candidato deletado com sucesso.");
        }catch(Exception ex){
            return ResponseEntity.badRequest().body("Erro ao deletar o candidato!");
        }
    }
}
