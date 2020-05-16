package com.calarix.sample.testing.customer.repository;

import com.calarix.sample.testing.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select c from Customer c where upper(c.fullName) like " + "%" + ":fullName" + "%")
    public List<Customer> findByFullName(@Param("fullName") String fullName);

    @Query("select c from Customer c where upper(c.email)  = :email")
    public Customer findByEmail(@Param("email") String email);


}
