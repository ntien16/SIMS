package com.sims.entitynew;

import com.sims.entitynew.Employees;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Report.class)
public class Report_ { 

    public static volatile SingularAttribute<Report, String> contentreport;
    public static volatile SingularAttribute<Report, Integer> reportid;
    public static volatile SingularAttribute<Report, Date> reportdate;
    public static volatile SingularAttribute<Report, Employees> employeeid;

}