package demo.spring.service1.service;

import demo.spring.service1.dto.CustomOperationResponse;
import demo.spring.service1.dto.Resource1Request;
import demo.spring.service1.dto.Resource1Response;

public interface Resource1Service {

  Resource1Response getResource1(Long id);

  Resource1Response createResource1(Resource1Request resource1);

  Resource1Response updateResource1(Long id, Resource1Request resource1);

  void deleteResource1(Long id);

  CustomOperationResponse customOperation(Resource1Request resource1);
}
