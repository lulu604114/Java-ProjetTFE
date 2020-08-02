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
@Table(name = "Cards", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "Card.findById", query = "SELECT c FROM Card c WHERE c.id=:cardId"),
        @NamedQuery(name = "Card.findAll", query = "SELECT c FROM Card c")
})
public class Card {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "label")
    private String label;
    @Basic
    @Column(name = "position")
    private String position;
    @Basic
    @Column(name = "icon")
    private String icon;
    @Basic
    @Column(name = "size")
    private String size;

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id &&
                Objects.equals(label, card.label) &&
                Objects.equals(position, card.position) &&
                Objects.equals(icon, card.icon) &&
                Objects.equals(size, card.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, position, icon, size);
    }
}
