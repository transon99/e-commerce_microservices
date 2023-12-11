package com.sondev.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Category extends AbstractMappedEntity<String> {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "category_name", columnDefinition = "char(50)")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private List<Gallery> imageUrls;

    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Product> products;

}
