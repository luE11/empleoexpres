package pra.lue11.empleoexpres.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static String saveFile(String fileName, MultipartFile multipartFile)
            throws IOException {
        Path uploadPath = Paths.get( "files-upload" );

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fullFileName = multipartFile.getOriginalFilename();
        String extension = fullFileName.substring(fullFileName.lastIndexOf(".") + 1);
        Path filePath = uploadPath.resolve( fileName + "." + extension.toLowerCase());
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {       
            throw new IOException("Could not save file: " + fileName, ioe);
        }
         
        return filePath.toString();
    }
}