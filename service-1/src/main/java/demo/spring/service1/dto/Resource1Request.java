package demo.spring.service1.dto;

import demo.spring.service1.entity.EmbeddableEntity;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class Resource1Request {

  private Set<String> collections = new HashSet<>();

  private EmbeddableEntity embeddedEntity;

  private String responseField1;
}
