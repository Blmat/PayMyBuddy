package com.projet6opcr.paymybuddy.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "relation")
public class Relation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "owner", foreignKey = @ForeignKey(name = "fk_relation_owner"))
    private User owner;

    @ManyToOne
    @JoinColumn(name = "buddy", foreignKey = @ForeignKey(name = "fk_relation_buddy"))
    private User buddy;

    @Override
    public String toString() {
        return  "Relation {" +
                "  owner=" + owner.getId() + " " + owner.getLastName() + " " + owner.getFirstName() + " " + owner.getEmail() +
                ", buddy=" + buddy.getId() + " " + buddy.getLastName() + " " + buddy.getFirstName() + " " + buddy.getEmail() +
                '}';
    }

    public Relation() {
        super();
    }

    public Relation(User owner, User buddy) {
        this.owner = owner;
        this.buddy = buddy;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
       // if (relation.id != null && this.id != null) return relation.id == this.id;
        if (relation.id != null && this.id != null) return relation.id.equals(this.id);
        return Objects.equals(owner, relation.owner) &&
                Objects.equals(buddy, relation.buddy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, buddy);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public User getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public Long getOwnerId() {
        return owner.getId();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @JsonIgnore
    public User getBuddy() {
        return buddy;
    }

    @JsonProperty("buddy")
    public String getBuddyDetail() {
        return toString();
    }

    public void setBuddy(User buddy) {
        this.buddy = buddy;
    }
}

