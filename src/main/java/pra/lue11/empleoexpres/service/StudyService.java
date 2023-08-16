package pra.lue11.empleoexpres.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import pra.lue11.empleoexpres.dto.CandidateStudyDTO;
import pra.lue11.empleoexpres.model.Person;
import pra.lue11.empleoexpres.model.PersonHasStudy;
import pra.lue11.empleoexpres.model.PersonStudyId;
import pra.lue11.empleoexpres.model.Study;
import pra.lue11.empleoexpres.model.inmutable.CandidateRelatedStudyView;
import pra.lue11.empleoexpres.repository.PersonHasStudyRepository;
import pra.lue11.empleoexpres.repository.StudyRepository;
import pra.lue11.empleoexpres.repository.view.CandidateRelatedStudyRepository;

import java.util.List;

/**
 * @author luE11 on 11/08/23
 */
@Service
@AllArgsConstructor
public class StudyService {

    private StudyRepository studyRepository;
    private PersonHasStudyRepository personHasStudyRepository;
    private CandidateRelatedStudyRepository candidateRelatedStudyRepository;

    public List<Study> getAllStudies(){
        return studyRepository.findAll();
    }

    public void insert(CandidateStudyDTO candidateStudyDTO, Person person){
        PersonHasStudy personHasStudy = candidateStudyDTO.toPersonHasStudy();
        personHasStudy.setPerson(person);
        personHasStudy.setStudy(findStudyById(candidateStudyDTO.getStudyId()));
        personHasStudyRepository.save(personHasStudy);
    } // Fragmento para reutilizar popover historial de empleos y estudios?

    public List<CandidateRelatedStudyView> findAllCandidateStudies(Integer personId){
        return candidateRelatedStudyRepository.findAllByPersonId(personId);
    }

    public void deleteCandidateStudy(Integer candidateStudyId, Person candidate){
        PersonStudyId id = new PersonStudyId(candidate.getId(), candidateStudyId);
        PersonHasStudy personHasStudy = personHasStudyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate study not found"));
        System.out.println(personHasStudy.getCertificateName()+" C");
        if(!personHasStudy.getPerson().equals(candidate))
            throw new AccessDeniedException("User can just delete its own studies");
        personHasStudyRepository.delete(personHasStudy);
    }

    private Study findStudyById(Integer id){
        return studyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Study not found"));
    }

}
