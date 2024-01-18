package be.tftic.spring.demo.api.model.form;


import be.tftic.spring.demo.api.validation.constraint.Password;
import be.tftic.spring.demo.domain.entity.Address;
import be.tftic.spring.demo.domain.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterForm(
        @NotBlank String username,
        @Password String password,
        @Valid @NotNull AddressForm address
) {

    public User toEntity(){
        User user = new User();
        user.setUsername( username );
        user.setPassword( password );
        user.setAddress( address.ToEmbeddable() );
        return user;
    }

    public record AddressForm(
           @Min(1) int number,
           @NotBlank String street,
           @Min(1) int zipcode,
           @NotBlank String city,
           @NotBlank String country
    ){
        public Address ToEmbeddable(){
            Address address = new Address();
            address.setNumber(number);
            address.setStreet(street);
            address.setZipcode(zipcode);
            address.setCity(city);
            address.setCountry(country);
            return address;
        }

    }

}
