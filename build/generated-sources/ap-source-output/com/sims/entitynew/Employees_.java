package com.sims.entitynew;

import com.sims.entitynew.Bill;
import com.sims.entitynew.Exceptions;
import com.sims.entitynew.Importitems;
import com.sims.entitynew.Report;
import com.sims.entitynew.Rolez;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Employees.class)
public class Employees_ { 

    public static volatile SingularAttribute<Employees, String> password;
    public static volatile SingularAttribute<Employees, String> address;
    public static volatile CollectionAttribute<Employees, Report> reportCollection;
    public static volatile SingularAttribute<Employees, Integer> phone;
    public static volatile SingularAttribute<Employees, Rolez> roleid;
    public static volatile CollectionAttribute<Employees, Exceptions> exceptionsCollection;
    public static volatile CollectionAttribute<Employees, Bill> billCollection;
    public static volatile SingularAttribute<Employees, Integer> employeeid;
    public static volatile CollectionAttribute<Employees, Importitems> importitemsCollection;
    public static volatile SingularAttribute<Employees, String> employeename;

}