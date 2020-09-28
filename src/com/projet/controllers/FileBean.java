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
import javax.persistence.EntityTransaction;
import java.io.*;
import java.util.List;


@Named("fileBean")
@RequestScoped
public class FileBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Message message = Message.getMessage(App.BUNDLE_MESSAGE);
    private Document document;
    private Document documentPerso;
    private List<Document> documents;
    private List<Document> filteredDocuments;
    private List<Document> documentsPerso;
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
        this.documentsPerso = this.service.getAllByUser(user);
    }

    /**
     * Send the patient file to the server
     * @param event
     * @throws IOException
     */
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        String fileName = file.getFileName();
        String path = "C:\\Patientfiles\\" + fileName;
        long fileSize = file.getSize();
        String format = file.getContentType();
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
        boolean perso = false;
        saveinDB(path, fileName, format, perso);
    }

    /**
     * Send the user file to the server
     * @param event
     * @throws IOException
     */
    public void handleFileUploadPerso(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        String fileName = file.getFileName();
        String path = "C:\\Userfiles\\" + fileName;
        long fileSize = file.getSize();
        String format = file.getContentType();
        InputStream myInputStream = file.getInputStream();
        FileOutputStream output = null;
        try {
            // Create folder (if it doesn't already exist)
            File folder = new File("C:\\Userfiles");
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
        boolean perso = true;
        saveinDB(path, fileName, format, perso);
    }

    /**
     * Save info file in db
     * @param path
     * @param fileName
     * @return
     */
    public String saveinDB(String path, String fileName, String format, boolean perso){

        FacesContext context = FacesContext.getCurrentInstance();
        PatientBean patientBean = context.getApplication().evaluateExpressionGet(context, "#{patientBean}", PatientBean.class);
        int idPatient = patientBean.getPatient().getId();
        PatientService servicePatient = new PatientService(Patient.class);
        Patient patient = servicePatient.getById(idPatient) ;
        FacesMessage message = null;
        boolean succes = false;
        switch (format){
            case "application/pdf":
                format = "pdf";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                format = "word";
            case "image/jpeg":
                format = "jpeg";
            default :
                format = "file";
        }
        DocumentService service = new DocumentService(Document.class);
        EntityTransaction transaction = service.getTransaction();
        transaction.begin();
        try {
            if(perso)
            {
                Document documentUploaded = service.saveFilePerso(fileName, path, this.user, format);
                this.document = service.save(documentUploaded);
            }
            else{
                Document documentUploaded = service.saveFile(fileName, path, patient, this.user, format);
                this.document = service.save(documentUploaded);
            }

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
            externalContext.setResponseContentType(document.getFormat());
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

    /**
     * makes files inactive
     */
    public void delete() {
        DocumentService service = new DocumentService(Document.class);
        EntityTransaction transaction = this.service.getTransaction();
        System.out.println("suppression du document n " + document.getId());
        transaction.begin();
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

    public Document getDocumentPerso() {
        return documentPerso;
    }

    public void setDocumentPerso(Document documentPerso) {
        this.documentPerso = documentPerso;
    }

    public List<Document> getDocumentsPerso() {
        return documentsPerso;
    }

    public void setDocumentsPerso(List<Document> documentsPerso) {
        this.documentsPerso = documentsPerso;
    }
}