package com.edition.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cvs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom affiché du CV
    private String name;

    // Nom de fichier stocké sur le serveur
    @Column(nullable = false)
    private String filename;

    // Nom d'utilisateur propriétaire (optionnel)
    private String owner;

    @Column(length = 2000)
    private String description;

    private LocalDateTime uploadedAt = LocalDateTime.now();
}
