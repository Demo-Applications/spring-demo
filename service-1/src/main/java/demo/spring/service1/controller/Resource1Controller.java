package demo.spring.service1.controller;

import demo.spring.service1.dto.CustomOperationResponse;
import demo.spring.service1.dto.Resource1Request;
import demo.spring.service1.dto.Resource1Response;
import demo.spring.service1.service.Resource1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource1s")
public class Resource1Controller {

  @Autowired private Resource1Service resource1Service;

  private static final Logger logger = LoggerFactory.getLogger(Resource1Controller.class);

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Resource1Response getResource1(
      @PathVariable("id") Long id,
      @RequestParam(name = "param1", required = false) String param1,
      @RequestHeader(name = "header1", required = false) String header1) {
    logger.info("Received get resource1 request for id {} and header1 {}", id, header1);
    return resource1Service.getResource1(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Resource1Response createResource1(
      @RequestBody Resource1Request resource1,
      @RequestParam(name = "param1", required = false) String param1,
      @RequestHeader(name = "header1", required = false) String header1) {
    return resource1Service.createResource1(resource1);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Resource1Response updateResource1(
      @PathVariable("id") Long id,
      @RequestBody Resource1Request resource1,
      @RequestParam(name = "param1", required = false) String param1,
      @RequestHeader(name = "header1", required = false) String header1) {
    return resource1Service.updateResource1(id, resource1);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteResource1(
      @PathVariable("id") Long id,
      @RequestParam(name = "param1", required = false) String param1,
      @RequestHeader(name = "header1", required = false) String header1) {
    resource1Service.deleteResource1(id);
  }

  @RequestMapping(
      value = {"/customOperation"},
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      headers = {"X-Custom-Header"},
      params = "!param2",
      method = {RequestMethod.GET, RequestMethod.POST})
  @ResponseStatus(HttpStatus.OK)
  public CustomOperationResponse cutomOperation(
      @RequestBody Resource1Request resource1,
      @RequestParam(name = "param1", required = false) String param1,
      @RequestHeader(name = "X-Custom-Header", required = false) String header1,
      @RequestAttribute("attr1") String attr1) {
    // access pre-existing request attributes created earlier (for example, by a
    // Servlet Filter or HandlerInterceptor
    return resource1Service.customOperation(resource1);
  }
}
