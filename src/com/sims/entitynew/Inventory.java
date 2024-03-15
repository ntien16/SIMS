/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.entitynew;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "INVENTORY")
@NamedQueries({
    @NamedQuery(name = "Inventory.findAll", query = "SELECT i FROM Inventory i"),
    @NamedQuery(name = "Inventory.findByItemid", query = "SELECT i FROM Inventory i WHERE i.itemid = :itemid"),
    @NamedQuery(name = "Inventory.findByItemname", query = "SELECT i FROM Inventory i WHERE i.itemname = :itemname"),
    @NamedQuery(name = "Inventory.findByCostPrice", query = "SELECT i FROM Inventory i WHERE i.costPrice = :costPrice"),
    @NamedQuery(name = "Inventory.findBySellPrice", query = "SELECT i FROM Inventory i WHERE i.sellPrice = :sellPrice"),
    @NamedQuery(name = "Inventory.findByInventoryquantity", query = "SELECT i FROM Inventory i WHERE i.inventoryquantity = :inventoryquantity")})
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Itemid")
    private Integer itemid;
    @Basic(optional = false)
    @Column(name = "Itemname")
    private String itemname;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "CostPrice")
    private BigDecimal costPrice;
    @Basic(optional = false)
    @Column(name = "SellPrice")
    private BigDecimal sellPrice;
    @Column(name = "INVENTORYQUANTITY")
    private Integer inventoryquantity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventory")
    private Collection<Exceptionsdetail> exceptionsdetailCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventory")
    private Collection<BILLDetail> bILLDetailCollection;
    @JoinColumn(name = "CATEGORYID", referencedColumnName = "CATEGORYID")
    @ManyToOne(optional = false)
    private Category categoryid;
    @JoinColumn(name = "STATUSID", referencedColumnName = "STATUSID")
    @ManyToOne
    private Statuz statusid;
    @JoinColumn(name = "UNITID", referencedColumnName = "UNITID")
    @ManyToOne(optional = false)
    private Unit unitid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventory")
    private Collection<Itemstakebackdetail> itemstakebackdetailCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventory")
    private Collection<Importdetail> importdetailCollection;

    public Inventory() {
    }

    public Inventory(Integer itemid) {
        this.itemid = itemid;
    }

    public Inventory(Integer itemid, String itemname, BigDecimal costPrice, BigDecimal sellPrice) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
    }



    public Inventory(String itemname, BigDecimal costPrice, BigDecimal sellPrice, Integer inventoryquantity, Statuz statusid, Category categoryid,  Unit unitid) {
        this.itemname = itemname;
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
        this.inventoryquantity = inventoryquantity;
        this.categoryid = categoryid;
        this.statusid = statusid;
        this.unitid = unitid;
    }
      
    public Integer getItemid() {
        return itemid;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getInventoryquantity() {
        return inventoryquantity;
    }

    public void setInventoryquantity(Integer inventoryquantity) {
        this.inventoryquantity = inventoryquantity;
    }

    public Collection<Exceptionsdetail> getExceptionsdetailCollection() {
        return exceptionsdetailCollection;
    }

    public void setExceptionsdetailCollection(Collection<Exceptionsdetail> exceptionsdetailCollection) {
        this.exceptionsdetailCollection = exceptionsdetailCollection;
    }

    public Collection<BILLDetail> getBILLDetailCollection() {
        return bILLDetailCollection;
    }

    public void setBILLDetailCollection(Collection<BILLDetail> bILLDetailCollection) {
        this.bILLDetailCollection = bILLDetailCollection;
    }

    public Category getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Category categoryid) {
        this.categoryid = categoryid;
    }

    public Statuz getStatusid() {
        return statusid;
    }

    public void setStatusid(Statuz statusid) {
        this.statusid = statusid;
    }

    public Unit getUnitid() {
        return unitid;
    }

    public void setUnitid(Unit unitid) {
        this.unitid = unitid;
    }

    public Collection<Itemstakebackdetail> getItemstakebackdetailCollection() {
        return itemstakebackdetailCollection;
    }

    public void setItemstakebackdetailCollection(Collection<Itemstakebackdetail> itemstakebackdetailCollection) {
        this.itemstakebackdetailCollection = itemstakebackdetailCollection;
    }

    public Collection<Importdetail> getImportdetailCollection() {
        return importdetailCollection;
    }

    public void setImportdetailCollection(Collection<Importdetail> importdetailCollection) {
        this.importdetailCollection = importdetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemid != null ? itemid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.itemid == null && other.itemid != null) || (this.itemid != null && !this.itemid.equals(other.itemid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Inventory[ itemid=" + itemid + " ]";
    }

}
