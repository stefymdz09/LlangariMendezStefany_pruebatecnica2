package com.development.appointmentmanagement.models;

import com.development.appointmentmanagement.models.Appointment;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-03-28T21:20:35")
@StaticMetamodel(Citizen.class)
public class Citizen_ extends User_ {

    public static volatile SingularAttribute<Citizen, String> lastName;
    public static volatile ListAttribute<Citizen, Appointment> appointments;
    public static volatile SingularAttribute<Citizen, String> document;
    public static volatile SingularAttribute<Citizen, String> name;

}