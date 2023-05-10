package demo.spring.service1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigInteger;
import lombok.Data;

@Data
@Embeddable
public class EmbeddableEntity {

  @Column(name = "emb_field1")
  private boolean embeddableField1;

  @Column(name = "emb_field2")
  private BigInteger embeddableField2;
}
