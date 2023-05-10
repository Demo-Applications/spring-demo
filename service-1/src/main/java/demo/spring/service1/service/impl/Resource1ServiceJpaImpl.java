package demo.spring.service1.service.impl;

import demo.spring.service1.dto.CustomOperationResponse;
import demo.spring.service1.dto.Resource1Request;
import demo.spring.service1.dto.Resource1Response;
import demo.spring.service1.entity.Resource1;
import demo.spring.service1.repository.Resource1Repository;
import demo.spring.service1.service.Resource1Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Resource1ServiceJpaImpl implements Resource1Service {

  private static final String STORED_PROC_CUSTOM_OPERATION = "P_CustomOperation";

  @Autowired private Resource1Repository resource1Repository;

  @PersistenceContext private EntityManager em;

  @Override
  // transaction is effectively read-only, allowing for corresponding optimizations at runtime.
  // - set the flush mode of a Hibernate Session to "FlushMode.MANUAL"
  // - suppress change detection on commit
  // REQUIRES_NEW : when called by a read-write tx, creates new tx suspending current, resumes later
  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public Resource1Response getResource1(Long id) {
    Resource1 resource1 =
        resource1Repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    Resource1Response resource1Response = new Resource1Response();
    // Decouple business model from view model
    // Manual mapping should be preferred
    BeanUtils.copyProperties(resource1, resource1Response);
    return resource1Response;
  }

  @Override
  @Transactional(rollbackFor = {SQLException.class})
  public Resource1Response createResource1(Resource1Request resource1Request) {
    // Validation, Other business Logic
    Resource1 resource1 = new Resource1();
    BeanUtils.copyProperties(resource1Request, resource1);
    Resource1 savedEntity = resource1Repository.save(resource1);
    Resource1Response resource1Response = new Resource1Response();
    BeanUtils.copyProperties(savedEntity, resource1Response);
    return resource1Response;
  }

  @Override
  @Transactional
  public Resource1Response updateResource1(Long id, Resource1Request resource1Request) {
    Resource1 existingResource1 =
        resource1Repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    BeanUtils.copyProperties(resource1Request, existingResource1);
    existingResource1.setEmbeddedProperty(resource1Request.getEmbeddedEntity());
    Resource1 updatedEntity = resource1Repository.save(existingResource1);

    Resource1Response resource1Response = new Resource1Response();
    BeanUtils.copyProperties(updatedEntity, resource1Response);
    return resource1Response;
  }

  @Override
  @Transactional
  public void deleteResource1(Long id) {
    Resource1 existingResource1 =
        resource1Repository.findById(id).orElseThrow(ResourceNotFoundException::new);
    resource1Repository.delete(existingResource1);
  }

  @Override
  @Transactional
  public CustomOperationResponse customOperation(Resource1Request resource1) {
    StoredProcedureQuery storedProcedure =
        em.createStoredProcedureQuery(STORED_PROC_CUSTOM_OPERATION);
    // set parameters
    storedProcedure.registerStoredProcedureParameter(
        "emb_field2", BigInteger.class, ParameterMode.IN);
    storedProcedure.registerStoredProcedureParameter("date", LocalDateTime.class, ParameterMode.IN);
    storedProcedure.registerStoredProcedureParameter("res1", BigInteger.class, ParameterMode.INOUT);
    storedProcedure.registerStoredProcedureParameter(
        "res2", LocalDateTime.class, ParameterMode.OUT);
    storedProcedure.setParameter("emb_field2", resource1.getEmbeddedEntity().getEmbeddableField2());
    storedProcedure.setParameter("date", LocalDateTime.now().minusDays(2));
    storedProcedure.setParameter(
        "res1", resource1.getEmbeddedEntity().getEmbeddableField2().intValue() % 14);
    // execute SP
    storedProcedure.execute();

    CustomOperationResponse customOperationResponse = new CustomOperationResponse();

    int res1 = (int) storedProcedure.getOutputParameterValue("res1");
    String res2 = (String) storedProcedure.getOutputParameterValue("res2");

    customOperationResponse.setCutomOperationReturnVal1(res2);
    customOperationResponse.setCutomOperationReturnVal2(res1);

    return customOperationResponse;
  }
}
