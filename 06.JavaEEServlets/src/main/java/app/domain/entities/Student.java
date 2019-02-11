package app.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    private Integer id;
    private String name;
    private String townName;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getTownName() {
        return this.townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    @Override
    public String toString() {
        return String.format("Id = %d, student name = %s, town = %s",
                this.id, this.name, this.townName);
    }
}
