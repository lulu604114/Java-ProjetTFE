package com.projet.entities;

import com.projet.enumeration.BillingStatusEnum;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
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
@Table(name = "Billings", schema = "c3_jsf_tfe")
public class Billing {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "reference")
    private String reference;
    @Basic
    @Column(name = "communication")
    private String communication;
    @Basic
    @Column(name = "createdAt")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Basic
    @Column(name = "updateAt")
    @Temporal(TemporalType.DATE)
    private Date updateAt;
    @Basic
    @Column(name = "dueAt")
    @Temporal(TemporalType.DATE)
    private Date dueAt;
    @Basic
    @Column(name = "sendAt")
    @Temporal(TemporalType.DATE)
    private Date sendAt;
    @Basic
    @Column(name = "status")
    private BillingStatusEnum status;
    @OneToMany(mappedBy = "bill")
    private List<Meeting> meetings;
    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = false)
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "bill", referencedColumnName = "id")
    private Billing bill;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getDueAt() {
        return dueAt;
    }

    public void setDueAt(Date dueAt) {
        this.dueAt = dueAt;
    }

    public Date getSendAt() {
        return sendAt;
    }

    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }

    public BillingStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BillingStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Billing billing = (Billing) o;
        return id == billing.id &&
                Objects.equals(reference, billing.reference) &&
                Objects.equals(communication, billing.communication) &&
                Objects.equals(createdAt, billing.createdAt) &&
                Objects.equals(updateAt, billing.updateAt) &&
                Objects.equals(dueAt, billing.dueAt) &&
                Objects.equals(sendAt, billing.sendAt) &&
                Objects.equals(status, billing.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reference, communication, createdAt, updateAt, dueAt, sendAt, status);
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Billing getBill() {
        return bill;
    }

    public void setBill(Billing bill) {
        this.bill = bill;
    }
}
