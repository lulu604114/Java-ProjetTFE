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
@Table(name = "Prices", schema = "jsf_tfe")
public class Price {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "base")
    private double base;
    @Basic
    @Column(name = "preferential")
    private double preferential;
    @Basic
    @Column(name = "nonPreferential")
    private double nonPreferential;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getPreferential() {
        return preferential;
    }

    public void setPreferential(double preferential) {
        this.preferential = preferential;
    }

    public double getNonPreferential() {
        return nonPreferential;
    }

    public void setNonPreferential(double nonPreferential) {
        this.nonPreferential = nonPreferential;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return id == price.id &&
                Double.compare(price.base, base) == 0 &&
                Double.compare(price.preferential, preferential) == 0 &&
                Double.compare(price.nonPreferential, nonPreferential) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, base, preferential, nonPreferential);
    }
}
