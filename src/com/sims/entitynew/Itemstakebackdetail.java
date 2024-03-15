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
@Table(name = "ITEMSTAKEBACKDETAIL")
@NamedQueries({
    @NamedQuery(name = "Itemstakebackdetail.findAll", query = "SELECT i FROM Itemstakebackdetail i"),
    @NamedQuery(name = "Itemstakebackdetail.findByItemtkid", query = "SELECT i FROM Itemstakebackdetail i WHERE i.itemstakebackdetailPK.itemtkid = :itemtkid"),
    @NamedQuery(name = "Itemstakebackdetail.findByItemid", query = "SELECT i FROM Itemstakebackdetail i WHERE i.itemstakebackdetailPK.itemid = :itemid"),
    @NamedQuery(name = "Itemstakebackdetail.findByQuantity", query = "SELECT i FROM Itemstakebackdetail i WHERE i.quantity = :quantity"),
    @NamedQuery(name = "Itemstakebackdetail.findByStatuz", query = "SELECT i FROM Itemstakebackdetail i WHERE i.statuz = :statuz")})
public class Itemstakebackdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemstakebackdetailPK itemstakebackdetailPK;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private int quantity;
    @Column(name = "statuz")
    private String statuz;
    @JoinColumn(name = "ITEMID", referencedColumnName = "Itemid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Inventory inventory;
    @JoinColumn(name = "ITEMTKID", referencedColumnName = "ITEMTKID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Itemstakeback itemstakeback;

    public Itemstakebackdetail() {
    }

    public Itemstakebackdetail(ItemstakebackdetailPK itemstakebackdetailPK) {
        this.itemstakebackdetailPK = itemstakebackdetailPK;
    }

    public Itemstakebackdetail(ItemstakebackdetailPK itemstakebackdetailPK, int quantity) {
        this.itemstakebackdetailPK = itemstakebackdetailPK;
        this.quantity = quantity;
    }

    public Itemstakebackdetail(int itemtkid, int itemid) {
        this.itemstakebackdetailPK = new ItemstakebackdetailPK(itemtkid, itemid);
    }

    public ItemstakebackdetailPK getItemstakebackdetailPK() {
        return itemstakebackdetailPK;
    }

    public void setItemstakebackdetailPK(ItemstakebackdetailPK itemstakebackdetailPK) {
        this.itemstakebackdetailPK = itemstakebackdetailPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatuz() {
        return statuz;
    }

    public void setStatuz(String statuz) {
        this.statuz = statuz;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Itemstakeback getItemstakeback() {
        return itemstakeback;
    }

    public void setItemstakeback(Itemstakeback itemstakeback) {
        this.itemstakeback = itemstakeback;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemstakebackdetailPK != null ? itemstakebackdetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemstakebackdetail)) {
            return false;
        }
        Itemstakebackdetail other = (Itemstakebackdetail) object;
        if ((this.itemstakebackdetailPK == null && other.itemstakebackdetailPK != null) || (this.itemstakebackdetailPK != null && !this.itemstakebackdetailPK.equals(other.itemstakebackdetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Itemstakebackdetail[ itemstakebackdetailPK=" + itemstakebackdetailPK + " ]";
    }

}
