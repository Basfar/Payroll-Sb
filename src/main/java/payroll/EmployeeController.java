/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mukhtar
 */
@RestController
public class EmployeeController {
    
  private final EmployeeRepository repository;

  EmployeeController(EmployeeRepository repository) {
    this.repository = repository;
  }
  
  @GetMapping("/employees")
  public List<Employee> all(){
      return repository.findAll();
  }
  
  @PostMapping("/employees")
  public Employee newEmployee(@RequestBody Employee newEmployee){
      return repository.save(newEmployee);
  }
  
  @GetMapping("/employees/{id}")
  public Employee one(@PathVariable Long id){
      return repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
  }
  
  @PutMapping("/employees/{id}")
  public Employee UpdateEmployee(@RequestBody Employee newEmployee, @PathVariable Long id){
      
    return repository.findById(id)
      .map(employee -> {
        employee.setName(newEmployee.getName());
        employee.setRole(newEmployee.getRole());
        return repository.save(employee);
      })
      .orElseGet(() -> {
        newEmployee.setId(id);
        return repository.save(newEmployee);
      });
  }
  
  @DeleteMapping("/employees/{id}")
  public void deleteEmployee(@PathVariable Long id){
       repository.deleteById(id);
  }
}
