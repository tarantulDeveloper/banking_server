package kg.devcats.server.service;


import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageStorageService {
    String uploadImage(MultipartFile file);

    List<String> uploadImages(MultipartFile[] files);
    Resource loadAsResource(String filename);

}
