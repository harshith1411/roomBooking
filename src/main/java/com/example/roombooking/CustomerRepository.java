package com.example.roombooking;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
}
