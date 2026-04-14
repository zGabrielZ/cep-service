package br.com.gabrielferreira.cep.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_ADDRESS")
public class AddressEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 8294536543667152363L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "CEP_EXTERNAL_ID", nullable = false, unique = true)
    private UUID cepExternalId;

    @Column(name = "POSTAL_CODE", nullable = false)
    private String postalCode;

    @Column(name = "STREET", nullable = false)
    private String street;

    @Column(name = "NUMBER", nullable = false)
    private String number;

    @Column(name = "COMPLEMENT")
    private String complement;

    @Column(name = "NEIGHBORHOOD", nullable = false)
    private String neighborhood;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STATE", nullable = false)
    private String state;

    @PrePersist
    public void generateCepExternalId() {
        this.cepExternalId = UUID.randomUUID();
    }
}
