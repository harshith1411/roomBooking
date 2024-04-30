package com.example.roombooking;

import org.springframework.data.repository.CrudRepository;

public interface BookedRepository extends CrudRepository<Booked, Long> {
}
