package entity.user;

import constants.Keys;

import javax.persistence.*;

/**
 * Created by szpt_user045 on 14.02.2020.
 */
@Entity
@Table(name = Keys.USERS)
public class User implements Keys {
    private int id;
    private Person person;
    private String language;
    private String theme;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = PERSON)
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

    @Basic
    @Column(name = LANGUAGE)
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = THEME)
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
