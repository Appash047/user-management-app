package com.gokulinfocare.userapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "First Name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "Last Name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotEmpty(message = "Phone Number is required")
    @Pattern(regexp = "\\d{10}", message = "Enter a valid 10-digit phone number")
    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email address")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotEmpty(message = "Address is required")
    @Size(max = 255, message = "Address should not exceed 255 characters")
    @Column(name = "address")
    private String address;

}

