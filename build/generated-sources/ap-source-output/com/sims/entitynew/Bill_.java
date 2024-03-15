package com.sims.entitynew;

import com.sims.entitynew.BILLDetail;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Employees;
import com.sims.entitynew.Itemstakeback;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Bill.class)
public class Bill_ { 

    public static volatile SingularAttribute<Bill, Date> createdDate;
    public static volatile CollectionAttribute<Bill, Itemstakeback> itemstakebackCollection;
    public static volatile SingularAttribute<Bill, Integer> billID;
    public static volatile SingularAttribute<Bill, Customer> customerId;
    public static volatile CollectionAttribute<Bill, BILLDetail> bILLDetailCollection;
    public static volatile SingularAttribute<Bill, Employees> employeeid;

}