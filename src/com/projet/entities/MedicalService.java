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
@Table(name = "MedicalServices", schema = "c3_jsf_tfe")
public class MedicalService {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "code")
    private String code;
    @ManyToOne
    @JoinColumn(name = "price", referencedColumnName = "id", nullable = false)
    private Price price;
    @ManyToOne
    @JoinColumn(name = "pathology", referencedColumnName = "id", nullable = false)
    private Pathology pathology;
    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false)
    private MedicalServiceType medicalServiceType;
    @ManyToOne
    @JoinColumn(name = "placeType", referencedColumnName = "id", nullable = false)
    private PlaceType placeType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalService that = (MedicalService) o;
        return id == that.id &&
                Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    public com.projet.entities.Price getPrice() {
        return price;
    }

    public void setPrice(com.projet.entities.Price price) {
        this.price = price;
    }

    public Pathology getPathology() {
        return pathology;
    }

    public void setPathology(Pathology pathology) {
        this.pathology = pathology;
    }

    public MedicalServiceType getMedicalServiceType() {
        return medicalServiceType;
    }

    public void setMedicalServiceType(MedicalServiceType medicalServiceType) {
        this.medicalServiceType = medicalServiceType;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }
}
