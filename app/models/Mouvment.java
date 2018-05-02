package models;

import play.data.format.Formats;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mouvment")
public class Mouvment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;


    @ManyToOne
    private Ngo ngo;

    @ManyToOne
    private Volunteer volunteer;

    @Column(name = "montant")
    private Long montant;

    @Column(name = "date")
    @Formats.DateTime(pattern = "dd-MM-yyyy")
    private Date date;

    public Mouvment() {
    }

    public Mouvment(Ngo ngo, Volunteer volunteer, Long montant, Date date) {
        this.ngo = ngo;
        this.volunteer = volunteer;
        this.montant = montant;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ngo getNgo() {
        return ngo;
    }

    public void setNgo(Ngo ngo) {
        this.ngo = ngo;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
