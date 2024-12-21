package br.com.kesyodev.gestao_vagas.modules.candidate.useCases;


import br.com.kesyodev.gestao_vagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public void execute(String username){
        var candidateToDelete = candidateRepository.findByUsername(username);

        if(candidateToDelete.isPresent()){
            candidateRepository.delete(candidateToDelete.get());
        }else{
            throw new RuntimeException("Candidato não encontrado.");
        }

    }

}
