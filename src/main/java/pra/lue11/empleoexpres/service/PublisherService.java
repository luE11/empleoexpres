package pra.lue11.empleoexpres.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pra.lue11.empleoexpres.dto.PublisherDTO;
import pra.lue11.empleoexpres.model.Publisher;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.repository.PublisherRepository;
import pra.lue11.empleoexpres.utils.FileUploadUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author luE11 on 21/07/23
 */
@Service
@AllArgsConstructor
public class PublisherService {
    private PublisherRepository publisherRepository;
    private UserService userService;

    public boolean userExistsByEmail(String email) {
        return userService.userExistsByEmail(email);
    }

    public Publisher insert(PublisherDTO publisherDTO) throws IOException {
        User user = userService.insert(publisherDTO.generateUser());
        Publisher publisher = publisherDTO.generateCompany();
        publisher.setUser(user);
        if(publisherDTO.getLogo()!=null && !publisherDTO.getLogo().isEmpty())
            publisher.setLogoUrl(uploadFile("logo_"+ publisher.getCompanyName(), publisherDTO.getLogo()));
        return publisherRepository.save(publisher);
    }

    public List<Publisher> getAllPublishers(){
        return publisherRepository.findAll();
    }

    private String uploadFile(String name, MultipartFile file) throws IOException {
        return FileUploadUtil.saveFile(name, file);
    }
}
