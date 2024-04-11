package com.development.appointmentmanagement.models;

import com.development.appointmentmanagement.models.Administrator;
import com.development.appointmentmanagement.models.Appointment;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-03-28T21:20:35")
@StaticMetamodel(ProcedureTable.class)
public class ProcedureTable_ { 

    public static volatile SingularAttribute<ProcedureTable, Long> idProcedure;
    public static volatile ListAttribute<ProcedureTable, Appointment> appointments;
    public static volatile SingularAttribute<ProcedureTable, String> name;
    public static volatile SingularAttribute<ProcedureTable, String> description;
    public static volatile SingularAttribute<ProcedureTable, Administrator> admin;

}