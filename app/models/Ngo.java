package models;


import javax.persistence.*;

@Entity
@Table(name = "ngo")
public class Ngo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "wallet")
    private Long wallet;

    @Column(name = "status")    // 0 - 1
    private Long status;

    public Ngo() {
    }

    public Ngo(String name, Long wallet, Long status) {
        this.name = name;
        this.wallet = wallet;
        this.status = status;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }
}

