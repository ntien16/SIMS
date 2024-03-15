package com.sims.entitynew;

import com.sims.entitynew.Importitems;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Supplier.class)
public class Supplier_ { 

    public static volatile SingularAttribute<Supplier, Integer> supplierid;
    public static volatile SingularAttribute<Supplier, String> suppilerphone;
    public static volatile SingularAttribute<Supplier, String> suppilerAddress;
    public static volatile SingularAttribute<Supplier, String> suppilerName;
    public static volatile CollectionAttribute<Supplier, Importitems> importitemsCollection;

}