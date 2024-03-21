package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product")
@EqualsAndHashCode(of = {"title", "price"})
// BEGIN
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long Id;
    private String title;
    private Integer price;
}
// END
