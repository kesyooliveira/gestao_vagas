package br.com.kesyodev.gestao_vagas.modules.company.controllers;

import br.com.kesyodev.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.kesyodev.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.kesyodev.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.kesyodev.gestao_vagas.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static br.com.kesyodev.gestao_vagas.utils.TestUtils.objectToJSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach()
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    public void shouldBeAbleToCreateNewJob() throws Exception {

        var company = CompanyEntity.builder()
                .Description("COMPANY_DESCRIPTION")
                .email("EMAIL@COMPANY.COM")
                .password("1234567890")
                .username("COMPANY_USERNAME")
                .name("COMPANY_NAME")
                .build();

        companyRepository.saveAndFlush(company);

        var createJobDTO = CreateJobDTO.builder().benefits("BENEFITS_TEST").description("DESCRIPTION_TEST").level("LEVEL_TEST").build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJSON(createJobDTO))
                        .header("Authorization", TestUtils.generateToken(company.getId(),"my-secret-key")))
                .andExpect(MockMvcResultMatchers.status().isOk());
        System.out.println(result);

    }

}
