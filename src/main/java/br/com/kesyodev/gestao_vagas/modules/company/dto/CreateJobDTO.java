package br.com.kesyodev.gestao_vagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {

    @Schema(example = "Vaga para pessoa desenvoldedora jr", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example = "Plano de saúde, GYMPass", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;

    @Schema(example = "JUNIOR", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;

}
