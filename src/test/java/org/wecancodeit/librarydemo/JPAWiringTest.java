package org.wecancodeit.librarydemo;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.wecancodeit.librarydemo.models.Author;
import org.wecancodeit.librarydemo.models.Book;
import org.wecancodeit.librarydemo.models.Campus;
import org.wecancodeit.librarydemo.repositories.AuthorRepository;
import org.wecancodeit.librarydemo.repositories.BookRepository;
import org.wecancodeit.librarydemo.repositories.CampusRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JPAWiringTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CampusRepository campusRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private BookRepository bookRepo;


    @Test
    public void campusShouldHaveAListOfBooks(){
        Campus testCampus = new Campus("Test Location");
        Campus testCampus2 = new Campus("Test Location2");
        Author testAuthor1 = new Author("Test firstName", "Test lastName");
        Author testAuthor2 = new Author("Test firstName", "Test lastName2");
        Book testBook1 = new Book("Title", "Description", testCampus, testAuthor1);
        Book testBook2 = new Book("Title", "Description", testCampus, testAuthor1);

        campusRepo.save(testCampus);
        campusRepo.save(testCampus2);
        authorRepo.save(testAuthor1);
        authorRepo.save(testAuthor2);
        bookRepo.save(testBook1);
        bookRepo.save(testBook2);

        entityManager.flush();
        entityManager.clear();

        Optional<Campus> retrievedCampusOpt = campusRepo.findById(testCampus.getId());
        Campus retrievedCampus = retrievedCampusOpt.get();
        assertThat(retrievedCampus.getBooks()).contains(testBook1);
    }

    @Test
    public void booksShouldBeAbleToHaveMultipleAuthors(Book retrievedBook){
        Campus testCampus = new Campus("Test Location");
        Author testAuthor1 = new Author("Test firstName", "Test lastName");
        Author testAuthor2 = new Author("Test firstName", "Test lastName2");
        Book testBook1 = new Book("Title", "Description", testCampus, testAuthor1, testAuthor2);
        Book testBook2 = new Book("Title", "Description", testCampus, testAuthor1);
        Book testBook3 = new Book("Title", "Description", testCampus, testAuthor1);
        campusRepo.save(testCampus);
        authorRepo.save(testAuthor1);
        authorRepo.save(testAuthor2);
        bookRepo.save(testBook1);
        bookRepo.save(testBook2);
        bookRepo.save(testBook3);

        entityManager.flush();
        entityManager.clear();

        Book retrieveBook = bookRepo.findById(testBook1.getId()).get();
        Author retrieveAuthor1 = authorRepo.findById(testAuthor1.getId()).get();
        Author retrieveAuthor2 = authorRepo.findById(testAuthor2.getId()).get();
        assertThat(retrievedBook.getAuthors()).contains(testAuthor1, testAuthor2);
    }

}
