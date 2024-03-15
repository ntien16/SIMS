package com.sims.entitynew;

import com.sims.entitynew.Bill;
import com.sims.entitynew.Customer;
import com.sims.entitynew.Inventory;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Itemstakeback.class)
public class Itemstakeback_ { 

    public static volatile SingularAttribute<Itemstakeback, Date> canceldate;
    public static volatile SingularAttribute<Itemstakeback, Inventory> itemid;
    public static volatile SingularAttribute<Itemstakeback, Integer> quantity;
    public static volatile SingularAttribute<Itemstakeback, String> statuz;
    public static volatile SingularAttribute<Itemstakeback, Bill> billid;
    public static volatile SingularAttribute<Itemstakeback, Customer> customerid;
    public static volatile SingularAttribute<Itemstakeback, Integer> itemtkid;

}