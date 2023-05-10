package demo.spring.commons.entities;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity {
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
