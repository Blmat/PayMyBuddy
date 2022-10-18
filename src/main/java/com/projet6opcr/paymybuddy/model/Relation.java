package com.projet6opcr.paymybuddy.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "relation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Relation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "owner")
    private UserAccount owner;

    @ManyToOne
    @JoinColumn(name = "buddy")
    private UserAccount buddy;

    public Relation(UserAccount owner, UserAccount buddy) {
        this.owner = owner;
        this.buddy = buddy;
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
}

