package pra.lue11.empleoexpres.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pra.lue11.empleoexpres.model.Study;
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
    private CandidateRelatedStudyRepository candidateRelatedStudyRepository;

    public List<Study> getAllStudies(){
        return studyRepository.findAll();
    }

}
