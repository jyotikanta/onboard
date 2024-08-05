package com.arcticbear.onboard;

import com.arcticbear.onboard.exception.CustomerNotFoundException;
import com.arcticbear.onboard.objects.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void save(Customer customer){
        customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(()-> new CustomerNotFoundException("Customer not found with id : " + id));
    }

    public boolean existById(Long id) {
        return customerRepository.existsById(id);
    }

    public void update(Long id, Customer customer) {
        if(!existById(id)){
            throw new CustomerNotFoundException("Customer not found with id" + id);
        }
        customer.setId(id);
        customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void delete(Long id) {
        if(!existById(id)){
            throw new CustomerNotFoundException("Customer not found with id" + id);
        }
        customerRepository.deleteById(id);
    }
}
