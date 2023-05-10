package demo.spring.service1.repository;

import demo.spring.service1.entity.Resource1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Resource1Repository extends JpaRepository<Resource1, Long> {}
