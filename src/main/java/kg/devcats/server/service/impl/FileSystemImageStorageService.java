package kg.devcats.server.service.impl;

import kg.devcats.server.config.ImageStorageConfig;
import kg.devcats.server.exception.ImageIsEmptyException;
import kg.devcats.server.exception.ImageNotAppropriateException;
import kg.devcats.server.exception.ImageStorageException;
import kg.devcats.server.service.ImageStorageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileSystemImageStorageService implements ImageStorageService {


    @Value("${server.host}")
    String host;

    Path rootPath;


    public FileSystemImageStorageService(ImageStorageConfig imageStorageConfig) {
        rootPath = Paths.get(imageStorageConfig.path());
        init();
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            checkImage(file);
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path destinationFile = rootPath.resolve(Paths.get(fileName))
                    .normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            return host + "/api/images/" + fileName;
        }catch (IOException e) {
            log.error(e.getMessage());
            throw new ImageStorageException("Failed to store file");
        }

    }

    @Override
    public List<String> uploadImages(MultipartFile[] files) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            urls.add(uploadImage(file));
        }
        return urls;
    }


    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new ImageStorageException("Could not read file");

            }
        }
        catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new ImageStorageException("Could not read file");
        }
    }


    private Path load(String filename) {
        return rootPath.resolve(filename);
    }

    private void checkImage(MultipartFile file) {
        if (!(Objects.requireNonNull(file.getContentType()).toLowerCase().startsWith("image/")))
            throw new ImageNotAppropriateException();
        if (file.isEmpty()) throw new ImageIsEmptyException();
    }

    private void init() {
        if (!Files.exists(rootPath)) {
            try {
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new ImageStorageException("Could not initialize storage");
            }
        }
    }


}
