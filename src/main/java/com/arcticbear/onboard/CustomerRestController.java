package com.arcticbear.onboard;

import com.arcticbear.onboard.objects.Customer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class CustomerRestController {
    @Autowired
    CustomerService customerService;

    @GetMapping()
    public ResponseEntity<String> index(){
        return new ResponseEntity<>("Hello From Arctic!", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Customer customer){
        customerService.save(customer);
        return new ResponseEntity<>("Customer registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer){
        customerService.update(id, customer);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        customerService.delete(id);
        return new ResponseEntity<>("Customer deleted successfully" , HttpStatus.OK);
    }

    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

}
