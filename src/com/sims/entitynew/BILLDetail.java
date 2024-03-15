/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.entitynew;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "BILLDetail")
@NamedQueries({
    @NamedQuery(name = "BILLDetail.findAll", query = "SELECT b FROM BILLDetail b"),
    @NamedQuery(name = "BILLDetail.findByBillID", query = "SELECT b FROM BILLDetail b WHERE b.bILLDetailPK.billID = :billID"),
    @NamedQuery(name = "BILLDetail.findByItemid", query = "SELECT b FROM BILLDetail b WHERE b.bILLDetailPK.itemid = :itemid"),
    @NamedQuery(name = "BILLDetail.findByQuantity", query = "SELECT b FROM BILLDetail b WHERE b.quantity = :quantity"),
    @NamedQuery(name = "BILLDetail.findByDiscount", query = "SELECT b FROM BILLDetail b WHERE b.discount = :discount"),
    @NamedQuery(name = "BILLDetail.findByStatuz", query = "SELECT b FROM BILLDetail b WHERE b.statuz = :statuz")})
public class BILLDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BILLDetailPK bILLDetailPK;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "DISCOUNT")
    private Integer discount;
    @Basic(optional = false)
    @Column(name = "STATUZ")
    private String statuz;
    @JoinColumn(name = "BillID", referencedColumnName = "BillID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bill bill;
    @JoinColumn(name = "ITEMID", referencedColumnName = "Itemid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Inventory inventory;

    public BILLDetail() {
    }

    public BILLDetail(BILLDetailPK bILLDetailPK) {
        this.bILLDetailPK = bILLDetailPK;
    }

    public BILLDetail(BILLDetailPK bILLDetailPK, int quantity, String statuz) {
        this.bILLDetailPK = bILLDetailPK;
        this.quantity = quantity;
        this.statuz = statuz;
    }

    public BILLDetail(int billID, int itemid) {
        this.bILLDetailPK = new BILLDetailPK(billID, itemid);
    }

    public BILLDetailPK getBILLDetailPK() {
        return bILLDetailPK;
    }

    public void setBILLDetailPK(BILLDetailPK bILLDetailPK) {
        this.bILLDetailPK = bILLDetailPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getStatuz() {
        return statuz;
    }

    public void setStatuz(String statuz) {
        this.statuz = statuz;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bILLDetailPK != null ? bILLDetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BILLDetail)) {
            return false;
        }
        BILLDetail other = (BILLDetail) object;
        if ((this.bILLDetailPK == null && other.bILLDetailPK != null) || (this.bILLDetailPK != null && !this.bILLDetailPK.equals(other.bILLDetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.BILLDetail[ bILLDetailPK=" + bILLDetailPK + " ]";
    }

}
