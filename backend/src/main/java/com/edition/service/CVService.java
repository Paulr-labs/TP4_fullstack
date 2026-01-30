package com.edition.service;

import com.edition.model.CV;
import com.edition.repository.CVRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CVService {

    private final CVRepository cvRepository;

    @Value("${cvs.upload-dir:uploads/cvs}")
    private String uploadDir;

    public List<CV> findAll() {
        return cvRepository.findAll();
    }

    public Optional<CV> findById(Long id) {
        return cvRepository.findById(id);
    }

    public CV store(MultipartFile file, String name, String owner, String description) throws IOException {
        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(dir);

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path target = dir.resolve(filename);
        file.transferTo(target);

        CV cv = new CV();
        cv.setName(name != null && !name.isBlank() ? name : file.getOriginalFilename());
        cv.setFilename(filename);
        cv.setOwner(owner);
        cv.setDescription(description);
        cv.setUploadedAt(LocalDateTime.now());

        return cvRepository.save(cv);
    }

    public Resource loadAsResource(String filename) throws MalformedURLException {
        Path file = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(filename).normalize();
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        }
        return null;
    }
}
