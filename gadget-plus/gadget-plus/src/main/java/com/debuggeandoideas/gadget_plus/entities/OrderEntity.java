package com.debuggeandoideas.gadget_plus.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.action.internal.OrphanRemovalAction;

@Entity
@Table(name="orders")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(length = 32, nullable = false)
  private String clientName;

  @ToString.Exclude
  @OneToOne(fetch = FetchType.EAGER,
      cascade = {CascadeType.DETACH, CascadeType.REMOVE})
  @JoinColumn(name="id_bill", nullable = false, unique = true)
  private BillEntity bill;

  @OneToMany(mappedBy = "order",
      fetch = FetchType.EAGER,
      cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProductEntity> product = new ArrayList<>();

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderEntity that = (OrderEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
