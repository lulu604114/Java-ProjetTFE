package com.projet.entities;

import javax.persistence.*;
import java.util.Objects;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author nathan
 * @project TFE
 * Date: 27/07/2020
 * Time: 19:28
 * =================================================================
 */
@Entity
@Table(name = "Documents", schema = "jsf_tfe")

@NamedQueries({
        @NamedQuery(name = "Document.findByUser", query = "SELECT d FROM Document d WHERE d.user=:user AND d.patient=:patient"),
        @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d")
})
public class Document {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "path")
    private String path;
    @Basic
    @Column(name = "nom")
    private String nom;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = false)
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = true)
    private DocumentType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return id == document.id &&
                Objects.equals(path, document.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, nom);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }
}
