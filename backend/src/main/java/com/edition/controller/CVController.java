package com.edition.controller;

import com.edition.model.CV;
import com.edition.service.CVService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/cvs")
@RequiredArgsConstructor
public class CVController {

    private final CVService cvService;

    @GetMapping
    public List<CV> listAll() {
        return cvService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CV> getOne(@PathVariable Long id) {
        return cvService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "description", required = false) String description,
                                    @RequestParam(value = "owner", required = false) String owner) {
        try {
            CV saved = cvService.store(file, name, owner, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload");
        }
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Resource resource = cvService.loadAsResource(filename);
            if (resource == null) return ResponseEntity.notFound().build();

            String contentType = MediaType.APPLICATION_PDF_VALUE;

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
