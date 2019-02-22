package app.jsf_testing;

import app.classes.Person;

import javax.inject.Named;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Named("testing")
public class JustTesting {
    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private List<String> someStrings;
    private LocalDate localDate;
    private Date date;
    private LocalDateTime localDateTime;
    private List<Person> people;

    public JustTesting() {
        this.someStrings = new ArrayList<>(4);
        this.someStrings.add("Sasho");
        this.someStrings.add("Tosho");
        this.someStrings.add("Pesho");
        this.someStrings.add("Gosho");

        this.localDate = LocalDate.now();
        this.date = Date.valueOf("2019-02-29");
        this.localDateTime = LocalDateTime.now();

        this.people = new ArrayList<>(4);
        people.add(new Person("Aleksandar", "Boev"));
        people.add(new Person("Pesho", "Peshov"));
        people.add(new Person("Gosho", "Goshev"));
        people.add(new Person("Tosho", "Toshev"));
    }

    public List<String> getSomeStrings() {
        return this.someStrings;
    }

    public void setSomeStrings(List<String> someStrings) {
        this.someStrings = someStrings;
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalDateTime getLocalDateTime() {
        return this.localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getCorrectTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return dateTimeFormatter.format(this.localDateTime);
    }

    public List<Person> getPeople() {
        return this.people;
    }
}
