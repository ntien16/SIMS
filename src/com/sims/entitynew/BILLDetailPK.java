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
public class BILLDetailPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "BillID")
    private int billID;
    @Basic(optional = false)
    @Column(name = "ITEMID")
    private int itemid;

    public BILLDetailPK() {
    }

    public BILLDetailPK(int billID, int itemid) {
        this.billID = billID;
        this.itemid = itemid;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
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
        hash += (int) billID;
        hash += (int) itemid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BILLDetailPK)) {
            return false;
        }
        BILLDetailPK other = (BILLDetailPK) object;
        if (this.billID != other.billID) {
            return false;
        }
        if (this.itemid != other.itemid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.BILLDetailPK[ billID=" + billID + ", itemid=" + itemid + " ]";
    }

}
