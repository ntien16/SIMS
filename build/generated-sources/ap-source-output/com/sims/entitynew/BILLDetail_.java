package com.sims.entitynew;

import com.sims.entitynew.BILLDetailPK;
import com.sims.entitynew.Bill;
import com.sims.entitynew.Inventory;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(BILLDetail.class)
public class BILLDetail_ { 

    public static volatile SingularAttribute<BILLDetail, Integer> quantity;
    public static volatile SingularAttribute<BILLDetail, String> statuz;
    public static volatile SingularAttribute<BILLDetail, BILLDetailPK> bILLDetailPK;
    public static volatile SingularAttribute<BILLDetail, Integer> discount;
    public static volatile SingularAttribute<BILLDetail, Bill> bill;
    public static volatile SingularAttribute<BILLDetail, Inventory> inventory;

}