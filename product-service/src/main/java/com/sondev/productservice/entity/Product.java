package com.sondev.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@EqualsAndHashCode(callSuper = true, exclude = {"category"})
@Entity
@Setter
@Getter
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends AbstractMappedEntity<String> {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;
  
    @Column(name = "product_name", nullable = false, columnDefinition = "char(255)")
    private String name;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "discount",columnDefinition = "decimal(10,2)")
    private Double discount;

    @Column(name = "price", columnDefinition = "decimal(10,2)")
    private Double price;

    @Column(name = "sale_price", columnDefinition = "decimal(10,2)")
    private Double salePrice;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<Image> imageUrls;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;
}
