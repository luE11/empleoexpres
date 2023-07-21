package pra.lue11.empleoexpres.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pra.lue11.empleoexpres.dto.CompanyDTO;
import pra.lue11.empleoexpres.model.Company;
import pra.lue11.empleoexpres.model.User;
import pra.lue11.empleoexpres.repository.CompanyRepository;
import pra.lue11.empleoexpres.utils.FileUploadUtil;

import java.io.IOException;

/**
 * @author luE11 on 21/07/23
 */
@Service
@AllArgsConstructor
public class CompanyService {
    private CompanyRepository companyRepository;
    private UserService userService;

    public boolean userExistsByEmail(String email) {
        return userService.userExistsByEmail(email);
    }

    public Company insert(CompanyDTO companyDTO) throws IOException {
        User user = userService.insert(companyDTO.generateUser());
        Company company = companyDTO.generateCompany();
        company.setUser(user);
        if(companyDTO.getLogo()!=null)
            company.setLogoUrl(uploadFile("logo_"+company.getName(), companyDTO.getLogo()));
        return companyRepository.save(company);
    }

    private String uploadFile(String name, MultipartFile file) throws IOException {
        return FileUploadUtil.saveFile(name, file);
    }
}
