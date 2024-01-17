package serg.shamiryan.accounts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import serg.shamiryan.accounts.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByMobileNumber(String mobileNumber);

    boolean existsByMobileNumber(String mobileNumber);
}

