package com.development.appointmentmanagement.models;

import com.development.appointmentmanagement.models.Citizen;
import com.development.appointmentmanagement.models.ProcedureTable;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2024-03-28T21:20:35")
@StaticMetamodel(Appointment.class)
public class Appointment_ { 

    public static volatile SingularAttribute<Appointment, LocalDateTime> dateTime;
    public static volatile SingularAttribute<Appointment, Citizen> citizen;
    public static volatile SingularAttribute<Appointment, Long> idAppointment;
    public static volatile SingularAttribute<Appointment, String> description;
    public static volatile SingularAttribute<Appointment, ProcedureTable> procedure;
    public static volatile SingularAttribute<Appointment, Boolean> status;

}