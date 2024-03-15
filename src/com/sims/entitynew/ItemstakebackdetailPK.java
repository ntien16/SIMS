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
public class ItemstakebackdetailPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ITEMTKID")
    private int itemtkid;
    @Basic(optional = false)
    @Column(name = "ITEMID")
    private int itemid;

    public ItemstakebackdetailPK() {
    }

    public ItemstakebackdetailPK(int itemtkid, int itemid) {
        this.itemtkid = itemtkid;
        this.itemid = itemid;
    }

    public int getItemtkid() {
        return itemtkid;
    }

    public void setItemtkid(int itemtkid) {
        this.itemtkid = itemtkid;
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
        hash += (int) itemtkid;
        hash += (int) itemid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemstakebackdetailPK)) {
            return false;
        }
        ItemstakebackdetailPK other = (ItemstakebackdetailPK) object;
        if (this.itemtkid != other.itemtkid) {
            return false;
        }
        if (this.itemid != other.itemid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.ItemstakebackdetailPK[ itemtkid=" + itemtkid + ", itemid=" + itemid + " ]";
    }

}
