package com.sondev.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Setter
@Getter
@Table(name = "banners")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class Banner extends AbstractMappedEntity<String> {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Column(name = "banner_name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "image_public_id")
    private String publicId;

}
