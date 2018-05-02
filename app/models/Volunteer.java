package models;

import javax.persistence.*;

@Entity
@Table(name = "volunteer")
public class Volunteer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "wallet")
    private Long wallet;

    public Volunteer() {
    }

    public Volunteer(String name, Long wallet) {
        this.name = name;
        this.wallet = wallet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWallet() {
        return wallet;
    }

    public void setWallet(Long wallet) {
        this.wallet = wallet;
    }
}
