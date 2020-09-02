package com.projet.controllers;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFiles;
import org.primefaces.shaded.commons.io.FilenameUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named("fileUploadBean")
@SessionScoped
public class FileUploadBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;
    private UploadedFile file;
    private UploadedFiles files;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UploadedFiles getFiles() {
        return files;
    }

    public void setFiles(UploadedFiles files) {
        this.files = files;
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void uploadMultiple() {
        if (files != null) {
            for (UploadedFile f : files.getFiles()) {
                FacesMessage message = new FacesMessage("Successful", f.getFileName() + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        String fileName = file.getFileName();
        long fileSize = file.getSize();
        InputStream myInputStream = file.getInputStream();
        FileOutputStream output = null;
        try {
            // Create folder (if it doesn't already exist)
//            File folder = new File("C:\\Users\\natha\\IdeaProjects\\Projet TFE\\files");
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
    }
}