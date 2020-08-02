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
@Table(name = "Places", schema = "jsf_tfe")
public class Place {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "label")
    private String label;
    @Basic
    @Column(name = "adress")
    private String adress;
    @Basic
    @Column(name = "streetNumber")
    private String streetNumber;
    @Basic
    @Column(name = "streetBox")
    private String streetBox;
    @Basic
    @Column(name = "postalCode")
    private String postalCode;
    @Basic
    @Column(name = "city")
    private String city;
    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false)
    private PlaceType placeType;
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    private User user;

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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetBox() {
        return streetBox;
    }

    public void setStreetBox(String streetBox) {
        this.streetBox = streetBox;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return id == place.id &&
                Objects.equals(label, place.label) &&
                Objects.equals(adress, place.adress) &&
                Objects.equals(streetNumber, place.streetNumber) &&
                Objects.equals(streetBox, place.streetBox) &&
                Objects.equals(postalCode, place.postalCode) &&
                Objects.equals(city, place.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, adress, streetNumber, streetBox, postalCode, city);
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public void setPlaceType(PlaceType placeType) {
        this.placeType = placeType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
