package kg.devcats.server.controller;


import kg.devcats.server.service.ImageStorageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api/images")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ImageController {

    ImageStorageService storageService;

    @SneakyThrows //TODO нужно поменять логику и контроллер переписать на endpoint
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + URLEncoder.encode(Objects.requireNonNull(file.getFilename()),
                                StandardCharsets.UTF_8).replaceAll("\\+", "%20") + "\"")
                .contentType(MediaType.parseMediaType(MediaType.parseMediaType
                        (Files.probeContentType(file.getFile().toPath())).toString())).body(file);
    }

}
