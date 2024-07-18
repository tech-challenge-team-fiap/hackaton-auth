package com.api.auth.api.authenticator.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="doctors")
public class Doctor extends User{

    private String crm;

    private String specialty;
}