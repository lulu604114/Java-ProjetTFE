package com.projet.services;

import com.projet.entities.Document;
import com.projet.entities.Patient;
import com.projet.entities.User;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DocumentService extends Service<Document> implements Serializable {
    private static final Logger log = Logger.getLogger(DocumentService.class);
    private static final long serialVersionUID = 1L;

    public DocumentService(){
        super();
    }

    public List<Document> getAllByPatient(User user, Patient patient) {
        Map<String, Object> param = new HashMap<>();
        param.put("patient", patient);
        param.put("user", user);
        return finder.findByNamedQuery("Document.findByUserAndPatient",param);
    }

    public List<Document> getAllByUser(User user) {
        Map<String, Object> param = new HashMap<>();
        param.put("user", user);
        return finder.findByNamedQuery("Document.findByUser",param);
    }

    @Override
    public Document save(Document document) {
        System.out.println("Je save");
        if (document.getId() == 0) {
            System.out.println("Je persist");
            em.persist(document);
        } else {
            document = em.merge(document);
        }
        return document;
    }

    public Document saveFile(String fileName, String path, Patient patient, User user, String format){
        Document document = new Document();
        document.setNom(fileName);
        document.setPath(path);
        document.setPatient(patient);
        document.setUser(user);
        document.setActive(true);
        document.setFormat(format);
        return document;
    }
    public Document saveFilePerso(String fileName, String path, User user, String format){
        Document document = new Document();
        document.setNom(fileName);
        document.setPath(path);
        document.setUser(user);
        document.setActive(true);
        document.setFormat(format);
        return document;
    }
    public Document deleteDocument(Document document){
        document.setActive(false);
        return document;
    }
}