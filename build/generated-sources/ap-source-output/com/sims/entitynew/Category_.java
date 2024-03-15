package com.sims.entitynew;

import com.sims.entitynew.Inventory;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-10-03T11:00:45", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Category.class)
public class Category_ { 

    public static volatile CollectionAttribute<Category, Inventory> inventoryCollection;
    public static volatile SingularAttribute<Category, String> categoryname;
    public static volatile SingularAttribute<Category, Integer> categoryid;

}