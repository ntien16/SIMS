/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.entitynew;

import java.io.Serializable;
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
@Table(name = "EXCEPTIONSDETAIL")
@NamedQueries({
    @NamedQuery(name = "Exceptionsdetail.findAll", query = "SELECT e FROM Exceptionsdetail e"),
    @NamedQuery(name = "Exceptionsdetail.findByExceptionId", query = "SELECT e FROM Exceptionsdetail e WHERE e.exceptionsdetailPK.exceptionId = :exceptionId"),
    @NamedQuery(name = "Exceptionsdetail.findByItemid", query = "SELECT e FROM Exceptionsdetail e WHERE e.exceptionsdetailPK.itemid = :itemid"),
    @NamedQuery(name = "Exceptionsdetail.findByExceptionType", query = "SELECT e FROM Exceptionsdetail e WHERE e.exceptionType = :exceptionType")})
public class Exceptionsdetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ExceptionsdetailPK exceptionsdetailPK;
    @Column(name = "ExceptionType")
    private String exceptionType;
    @JoinColumn(name = "ExceptionId", referencedColumnName = "ExceptionId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Exceptions exceptions;
    @JoinColumn(name = "ITEMID", referencedColumnName = "Itemid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Inventory inventory;

    public Exceptionsdetail() {
    }

    public Exceptionsdetail(ExceptionsdetailPK exceptionsdetailPK) {
        this.exceptionsdetailPK = exceptionsdetailPK;
    }

    public Exceptionsdetail(int exceptionId, int itemid) {
        this.exceptionsdetailPK = new ExceptionsdetailPK(exceptionId, itemid);
    }

    public ExceptionsdetailPK getExceptionsdetailPK() {
        return exceptionsdetailPK;
    }

    public void setExceptionsdetailPK(ExceptionsdetailPK exceptionsdetailPK) {
        this.exceptionsdetailPK = exceptionsdetailPK;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public Exceptions getExceptions() {
        return exceptions;
    }

    public void setExceptions(Exceptions exceptions) {
        this.exceptions = exceptions;
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
        hash += (exceptionsdetailPK != null ? exceptionsdetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exceptionsdetail)) {
            return false;
        }
        Exceptionsdetail other = (Exceptionsdetail) object;
        if ((this.exceptionsdetailPK == null && other.exceptionsdetailPK != null) || (this.exceptionsdetailPK != null && !this.exceptionsdetailPK.equals(other.exceptionsdetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Exceptionsdetail[ exceptionsdetailPK=" + exceptionsdetailPK + " ]";
    }

}
