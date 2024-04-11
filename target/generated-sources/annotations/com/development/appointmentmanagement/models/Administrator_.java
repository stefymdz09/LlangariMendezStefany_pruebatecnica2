package com.development.appointmentmanagement.models;

import com.development.appointmentmanagement.models.ProcedureTable;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-03-28T21:20:35")
@StaticMetamodel(Administrator.class)
public class Administrator_ extends User_ {

    public static volatile SingularAttribute<Administrator, String> firstName;
    public static volatile SingularAttribute<Administrator, String> lastName;
    public static volatile ListAttribute<Administrator, ProcedureTable> procedures;

}