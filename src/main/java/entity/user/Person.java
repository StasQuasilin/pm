package entity.user;

import constants.Keys;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 14.02.2020.
 */
@Entity
@Table(name = Keys.PERSON)
public class Person implements Keys{
    private int id;
    private String surname;
    private String forename;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = SURNAME)
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = FORENAME)
    public String getForename() {
        return forename;
    }
    public void setForename(String forename) {
        this.forename = forename;
    }
}
