package com.sims.entitynew;

import com.sims.entitynew.Bill;
import com.sims.entitynew.Itemstakeback;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, Integer> phoneNumber;
    public static volatile SingularAttribute<Customer, String> addressCus;
    public static volatile CollectionAttribute<Customer, Itemstakeback> itemstakebackCollection;
    public static volatile SingularAttribute<Customer, Integer> customerID;
    public static volatile CollectionAttribute<Customer, Bill> billCollection;
    public static volatile SingularAttribute<Customer, String> customerName;

}