package br.com.gabrielferreira.cep.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_ADDRESS_LOG")
public class AddressLogEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2494854416368464785L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
     private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", nullable = false)
     private AddressEntity address;

    @Column(name = "ADDRESS_LOG_EXTERNAL_ID", nullable = false, unique = true)
    private UUID addressLogExternalId;

     @Column(name = "LOGGED_AT", columnDefinition = "TIMESTAMP")
     private OffsetDateTime loggedAt;

    @PrePersist
    public void generateAddressLogExternalId() {
        this.addressLogExternalId = UUID.randomUUID();
    }
}
