package br.com.kesyodev.gestao_vagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateResponseDTO {

    @Schema(example = "Nome do candidato")
    private String name;
    @Schema(example = "Desenvolvedor java")
    private String description;
    @Schema(example = "joaosilva")
    private String username;
    @Schema(example = "joaosilva@gmail.com")
    private String email;
    private UUID id;


}
