package com.sondev.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "galery")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"product"})
@Builder
public class Gallery extends AbstractMappedEntity {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "image_public_id")
    private String publicId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
//    private Product product;
}
