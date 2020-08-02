package com.projet.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * =================================================================
 * Created by Intellij IDEA.
 *
 * @author lucas
 * @project TFE
 * Date: 27/01/2020
 * Time: 19:28
 * =================================================================
 */
@Entity
@Table(name = "MedicalCares", schema = "jsf_tfe")
public class MedicalCare {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "beginAt")
    @Temporal(TemporalType.DATE)
    private Date beginAt;
    @Basic
    @Column(name = "endAt")
    @Temporal(TemporalType.DATE)
    private Date endAt;
    @ManyToOne
    @JoinColumn(name = "agreement", referencedColumnName = "id")
    private Agreement agreement;
    @ManyToOne
    @JoinColumn(name = "medicalService", referencedColumnName = "id", nullable = false)
    private MedicalService medicalService;
    @ManyToOne
    @JoinColumn(name = "checkupDoctor", referencedColumnName = "id")
    private Contact checkupDoctor;
    @ManyToOne
    @JoinColumn(name = "meetingDoctor", referencedColumnName = "id")
    private Contact meetingDoctor;
    @ManyToOne
    @JoinColumn(name = "organization", referencedColumnName = "id")
    private Organization medicalInsurance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(Date beginAt) {
        this.beginAt = beginAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalCare that = (MedicalCare) o;
        return id == that.id &&
                Objects.equals(beginAt, that.beginAt) &&
                Objects.equals(endAt, that.endAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beginAt, endAt);
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public MedicalService getMedicalService() {
        return medicalService;
    }

    public void setMedicalService(MedicalService medicalService) {
        this.medicalService = medicalService;
    }

    public Contact getCheckupDoctor() {
        return checkupDoctor;
    }

    public void setCheckupDoctor(Contact checkupDoctor) {
        this.checkupDoctor = checkupDoctor;
    }

    public Contact getMeetingDoctor() {
        return meetingDoctor;
    }

    public void setMeetingDoctor(Contact meetingDoctor) {
        this.meetingDoctor = meetingDoctor;
    }

    public Organization getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(Organization medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }
}
