/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.entitynew;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Embeddable
public class ExceptionsdetailPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ExceptionId")
    private int exceptionId;
    @Basic(optional = false)
    @Column(name = "ITEMID")
    private int itemid;

    public ExceptionsdetailPK() {
    }

    public ExceptionsdetailPK(int exceptionId, int itemid) {
        this.exceptionId = exceptionId;
        this.itemid = itemid;
    }

    public int getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(int exceptionId) {
        this.exceptionId = exceptionId;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) exceptionId;
        hash += (int) itemid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExceptionsdetailPK)) {
            return false;
        }
        ExceptionsdetailPK other = (ExceptionsdetailPK) object;
        if (this.exceptionId != other.exceptionId) {
            return false;
        }
        if (this.itemid != other.itemid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.ExceptionsdetailPK[ exceptionId=" + exceptionId + ", itemid=" + itemid + " ]";
    }

}
