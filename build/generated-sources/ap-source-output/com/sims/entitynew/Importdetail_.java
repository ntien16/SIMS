package com.sims.entitynew;

import com.sims.entitynew.ImportdetailPK;
import com.sims.entitynew.Importitems;
import com.sims.entitynew.Inventory;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Importdetail.class)
public class Importdetail_ { 

    public static volatile SingularAttribute<Importdetail, Importitems> importitems;
    public static volatile SingularAttribute<Importdetail, Integer> quantity;
    public static volatile SingularAttribute<Importdetail, Inventory> inventory;
    public static volatile SingularAttribute<Importdetail, ImportdetailPK> importdetailPK;

}