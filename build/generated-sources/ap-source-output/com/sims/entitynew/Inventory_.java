package com.sims.entitynew;

import com.sims.entitynew.BILLDetail;
import com.sims.entitynew.Category;
import com.sims.entitynew.Exceptions;
import com.sims.entitynew.Importdetail;
import com.sims.entitynew.Itemstakeback;
import com.sims.entitynew.Unit;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Inventory.class)
public class Inventory_ { 

    public static volatile SingularAttribute<Inventory, Integer> inventoryquantity;
    public static volatile SingularAttribute<Inventory, Integer> itemid;
    public static volatile CollectionAttribute<Inventory, Importdetail> importdetailCollection;
    public static volatile SingularAttribute<Inventory, String> statuz;
    public static volatile SingularAttribute<Inventory, String> itemname;
    public static volatile CollectionAttribute<Inventory, Itemstakeback> itemstakebackCollection;
    public static volatile CollectionAttribute<Inventory, Exceptions> exceptionsCollection;
    public static volatile SingularAttribute<Inventory, BigDecimal> costPrice;
    public static volatile SingularAttribute<Inventory, Unit> unitid;
    public static volatile SingularAttribute<Inventory, BigDecimal> sellPrice;
    public static volatile CollectionAttribute<Inventory, BILLDetail> bILLDetailCollection;
    public static volatile SingularAttribute<Inventory, Category> categoryid;

}