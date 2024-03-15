package com.sims.entitynew;

import com.sims.entitynew.Employees;
import com.sims.entitynew.Inventory;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Exceptions.class)
public class Exceptions_ { 

    public static volatile SingularAttribute<Exceptions, String> exceptionType;
    public static volatile SingularAttribute<Exceptions, Inventory> itemid;
    public static volatile SingularAttribute<Exceptions, Integer> exceptionId;
    public static volatile SingularAttribute<Exceptions, Date> exceptionDate;
    public static volatile SingularAttribute<Exceptions, Employees> employeeid;

}