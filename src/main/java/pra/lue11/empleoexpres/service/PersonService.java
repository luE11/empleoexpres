package pra.lue11.empleoexpres.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pra.lue11.empleoexpres.dto.CandidateDTO;
import pra.lue11.empleoexpres.model.Person;
import pra.lue11.empleoexpres.model.Place;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.model.enums.JobModality;
import pra.lue11.empleoexpres.repository.PersonRepository;
import pra.lue11.empleoexpres.utils.FileUploadUtil;

import java.io.IOException;

/**
 * @author luE11 on 21/07/23
 * Service for candidates management
 */
@Service
@AllArgsConstructor
public class PersonService {

    private PersonRepository personRepository;
    private UserService userService;
    private PlaceService placeService;

    public JobModality[] getPreferredJobModalities(){
        return JobModality.class.getEnumConstants();
    }

    public void insert(CandidateDTO candidateDTO) throws IOException {
        Place place = placeService.findPlaceById(candidateDTO.getPlaceId());
        User user = userService.insert(candidateDTO.generateUser());
        Person person = candidateDTO.generatePerson();
        person.setUser(user);
        person.setPlace(place);
        person.setCv1Url(uploadFile("cv1_"+user.getEmail(), candidateDTO.getCv1()));
        if(candidateDTO.getCv2()!=null && !candidateDTO.getCv2().isEmpty())
            person.setCv2Url(uploadFile("cv2_"+user.getEmail(), candidateDTO.getCv2()));
        if(candidateDTO.getCv3()!=null && !candidateDTO.getCv3().isEmpty())
            person.setCv3Url(uploadFile("cv3_"+user.getEmail(), candidateDTO.getCv3()));
        if(candidateDTO.getPhoto()!=null && !candidateDTO.getPhoto().isEmpty())
            person.setPhotoUrl(uploadFile("profile_"+user.getEmail(), candidateDTO.getPhoto()));
        personRepository.save(person);
    }

    private String uploadFile(String name, MultipartFile file) throws IOException {
        return FileUploadUtil.saveFile(name, file);
    }

}
