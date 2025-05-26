package com.debuggeandoideas.gadget_plus.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="bill")
@Data
@ToString
public class BillEntity {

  @Id
  @Column(nullable = true, length = 64)
  private String id;

  @Column
  private BigDecimal totalAmount;

  @Column(name="client_rfc", length = 14, nullable = false)
  private String rfc;

}
