package com.projet.controllers;

import com.projet.conf.App;
import com.projet.entities.Document;
import com.projet.entities.Patient;
import com.projet.entities.User;
import com.projet.security.SecurityManager;
import com.projet.services.DocumentService;
import com.projet.services.PatientService;
import com.projet.utils.Message;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.inject.Named;
import java.io.*;
import java.util.List;
import javax.persistence.EntityTransaction;


@Named("fileBean")
@RequestScoped
public class FileBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Message message = Message.getMessage(App.BUNDLE_MESSAGE);
    private Document document;
    private List<Document> documents;
    private List<Document> filteredDocuments;
    private User user;
    private StreamedContent file;
    private DefaultStreamedContent download;
    DocumentService service = new DocumentService(Document.class);
    EntityTransaction transaction = this.service.getTransaction();
    FacesContext context = FacesContext.getCurrentInstance();
    PatientBean patientBean = context.getApplication().evaluateExpressionGet(context, "#{patientBean}", PatientBean.class);
    Patient patient = patientBean.getPatient();
    @PostConstruct
    public void onInit()
    {
        user = (User) SecurityManager.getSessionAttribute(App.SESSION_USER);
        this.documents = this.service.getAllByPatient(user, this.patient);
    }

    /**
     * Send the file to the server
     * @param event
     * @throws IOException
     */
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        String fileName = file.getFileName();
        String path = "C:\\Patientfiles\\" + fileName;
        long fileSize = file.getSize();
        InputStream myInputStream = file.getInputStream();
        FileOutputStream output = null;
        try {
            // Create folder (if it doesn't already exist)
            File folder = new File("C:\\Patientfiles");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Create output file
            output = new FileOutputStream(new File(folder, fileName));
            // Write data from input stream to output file.
            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = myInputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
            // Also close InputStream if no longer needed.
            try {
                if (myInputStream != null) {
                    myInputStream.close();
                }
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
        FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        saveinDB(path, fileName);
    }

    /**
     * Save info file in db
     * @param path
     * @param fileName
     * @return
     */
    public String saveinDB(String path, String fileName){

        FacesContext context = FacesContext.getCurrentInstance();
        PatientBean patientBean = context.getApplication().evaluateExpressionGet(context, "#{patientBean}", PatientBean.class);
        int idPatient = patientBean.getPatient().getId();
        FacesMessage message = null;
        boolean succes = false;
        PatientService servicePatient = new PatientService(Patient.class);
        Patient patient = servicePatient.getById(idPatient) ;
        DocumentService service = new DocumentService(Document.class);
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            Document document = service.saveFile(fileName, path, patient, this.user);
            this.document = service.save(document);
            transaction.commit();
            System.out.println("Je commit");
            succes = true;
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
                succes = false;
            }
            service.close();
        }
        this.documents.add(this.document);
        document = new Document();
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ajout du document avec succès", fileName);
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().addCallbackParam("loggedIn", succes);
        return "app/patient/viewPatient.xhtml";
    }

    /**
     * Download file from serveur
     * @param document
     */
    public void download(Document document) {
        System.out.println("Téléchargement du document : " + document.getNom());
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();

            externalContext.responseReset();
            externalContext.setResponseContentType("multipart/form-data");
            externalContext.setResponseHeader("Content-Disposition", "attachment;filename=" + document.getNom());

            FileInputStream inputStream = new FileInputStream(new File(document.getPath()));
            OutputStream outputStream = externalContext.getResponseOutputStream();

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            context.responseComplete();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public StreamedContent getFile() {
        System.out.println("getting file");
        return file;
    }

    public void delete() {
        System.out.println("suppression du document n) " + document.getId());
        this.transaction.begin();
        try {
            Document documentDeleted = service.deleteDocument(document);
            service.save(documentDeleted);
            transaction.commit();

        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            service.close();
        }
        this.documents.remove(document);
    }
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public List<Document> getFilteredDocuments() {
        return filteredDocuments;
    }

    public void setFilteredDocuments(List<Document> filteredDocuments) {
        this.filteredDocuments = filteredDocuments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void setFile(StreamedContent file) {
        this.file = file;
    }
}