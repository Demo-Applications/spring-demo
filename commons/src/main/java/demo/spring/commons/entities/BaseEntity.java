package demo.spring.commons.entities;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class BaseEntity {
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
