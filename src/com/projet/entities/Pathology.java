package com.projet.entities;

import javax.persistence.*;
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
@Table(name = "Pathologies", schema = "c3_jsf_tfe")
public class Pathology {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "label")
    private String label;
    @Basic
    @Column(name = "nomenclature")
    private String nomenclature;
    @Basic
    @Column(name = "meetingTotal")
    private String meetingTotal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public String getMeetingTotal() {
        return meetingTotal;
    }

    public void setMeetingTotal(String meetingTotal) {
        this.meetingTotal = meetingTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pathology pathology = (Pathology) o;
        return id == pathology.id &&
                Objects.equals(label, pathology.label) &&
                Objects.equals(nomenclature, pathology.nomenclature) &&
                Objects.equals(meetingTotal, pathology.meetingTotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, nomenclature, meetingTotal);
    }
}
