package com.projet.entities;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
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
@Table(name = "Fields", schema = "c3_jsf_tfe", catalog = "")
public class Field {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "intField")
    private Integer intField;
    @Basic
    @Column(name = "doubleField")
    private Double doubleField;
    @Basic
    @Column(name = "stringField")
    private String stringField;
    @Basic
    @Column(name = "dateField")
    @Temporal(TemporalType.DATE)
    private Date dateField;
    @Basic
    @Column(name = "dateTimeField")
    @Temporal(TemporalType.DATE)
    private Date dateTimeField;
    @Basic
    @Column(name = "timeField")
    @Temporal(TemporalType.TIME)
    private Date timeField;
    @Basic
    @Column(name = "booleanField")
    private Boolean booleanField;
    @ManyToOne
    @JoinColumn(name = "Information", referencedColumnName = "id", nullable = false)
    private Information information;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIntField() {
        return intField;
    }

    public void setIntField(Integer intField) {
        this.intField = intField;
    }

    public Double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(Double doubleField) {
        this.doubleField = doubleField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public Date getDateField() {
        return dateField;
    }

    public void setDateField(Date dateField) {
        this.dateField = dateField;
    }

    public Date getDateTimeField() {
        return dateTimeField;
    }

    public void setDateTimeField(Timestamp dateTimeField) {
        this.dateTimeField = dateTimeField;
    }

    public Date getTimeField() {
        return timeField;
    }

    public void setTimeField(Time timeField) {
        this.timeField = timeField;
    }

    public Boolean getBooleanField() {
        return booleanField;
    }

    public void setBooleanField(Boolean booleanField) {
        this.booleanField = booleanField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return id == field.id &&
                Objects.equals(intField, field.intField) &&
                Objects.equals(doubleField, field.doubleField) &&
                Objects.equals(stringField, field.stringField) &&
                Objects.equals(dateField, field.dateField) &&
                Objects.equals(dateTimeField, field.dateTimeField) &&
                Objects.equals(timeField, field.timeField) &&
                Objects.equals(booleanField, field.booleanField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, intField, doubleField, stringField, dateField, dateTimeField, timeField, booleanField);
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }
}
