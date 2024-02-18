package pl.school.management.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "SCHOOL")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "hour_price", scale = 2, nullable = false)
    private BigDecimal hourPrice;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getHourPrice() {
        return hourPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHourPrice(BigDecimal hourPrice) {
        this.hourPrice = hourPrice;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hourPrice=" + hourPrice +
                '}';
    }
}
