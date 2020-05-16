package com.calarix.sample.testing.customer;

import com.calarix.sample.testing.common.domain.Model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Customer extends Model {

    @Email
    @NotNull
    @NotEmpty
    public String email;
    public String RFC;
    public String fullName;
}
