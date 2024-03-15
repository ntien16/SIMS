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
@Table(name = "IMPORTDETAIL")
@NamedQueries({
    @NamedQuery(name = "Importdetail.findAll", query = "SELECT i FROM Importdetail i"),
    @NamedQuery(name = "Importdetail.findByImportid", query = "SELECT i FROM Importdetail i WHERE i.importdetailPK.importid = :importid"),
    @NamedQuery(name = "Importdetail.findByItemid", query = "SELECT i FROM Importdetail i WHERE i.importdetailPK.itemid = :itemid"),
    @NamedQuery(name = "Importdetail.findByQuantity", query = "SELECT i FROM Importdetail i WHERE i.quantity = :quantity")})
public class Importdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ImportdetailPK importdetailPK;
    @Basic(optional = false)
    @Column(name = "QUANTITY")
    private int quantity;
    @JoinColumn(name = "Importid", referencedColumnName = "Importid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Importitems importitems;
    @JoinColumn(name = "ITEMID", referencedColumnName = "Itemid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Inventory inventory;

    public Importdetail() {
    }

    public Importdetail(ImportdetailPK importdetailPK) {
        this.importdetailPK = importdetailPK;
    }

    public Importdetail(ImportdetailPK importdetailPK, int quantity) {
        this.importdetailPK = importdetailPK;
        this.quantity = quantity;
    }

    public Importdetail(int importid, int itemid) {
        this.importdetailPK = new ImportdetailPK(importid, itemid);
    }

    public ImportdetailPK getImportdetailPK() {
        return importdetailPK;
    }

    public void setImportdetailPK(ImportdetailPK importdetailPK) {
        this.importdetailPK = importdetailPK;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Importitems getImportitems() {
        return importitems;
    }

    public void setImportitems(Importitems importitems) {
        this.importitems = importitems;
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
        hash += (importdetailPK != null ? importdetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Importdetail)) {
            return false;
        }
        Importdetail other = (Importdetail) object;
        if ((this.importdetailPK == null && other.importdetailPK != null) || (this.importdetailPK != null && !this.importdetailPK.equals(other.importdetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Importdetail[ importdetailPK=" + importdetailPK + " ]";
    }

}
