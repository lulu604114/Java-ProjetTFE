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
@Table(name = "Agreements", schema = "c3_jsf_tfe")
public class Agreement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic
    @Column(name = "beginAt")
    @Temporal(TemporalType.DATE)
    private Date beginAt;
    @Basic
    @Column(name = "endAt")
    @Temporal(TemporalType.DATE)
    private Date endAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        Agreement agreement = (Agreement) o;
        return id == agreement.id &&
                Objects.equals(date, agreement.date) &&
                Objects.equals(beginAt, agreement.beginAt) &&
                Objects.equals(endAt, agreement.endAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, beginAt, endAt);
    }
}
