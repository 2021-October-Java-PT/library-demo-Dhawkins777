package org.wecancodeit.librarydemo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Campus {

    @Id
    @GeneratedValue
    private Long id;
    private String location;
    @OneToMany(mappedBy = "campus")
    private Collection<Book> books;

    public Long getId() {
        return  id;
    }

    public String getLocation() {
        return location;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public Campus(){
    }
    public Campus(String location) {
        this.location = location;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campus campus = (Campus) o;
        return Objects.equals(id, campus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
