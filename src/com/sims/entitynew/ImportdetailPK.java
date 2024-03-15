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
public class ImportdetailPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "Importid")
    private int importid;
    @Basic(optional = false)
    @Column(name = "ITEMID")
    private int itemid;

    public ImportdetailPK() {
    }

    public ImportdetailPK(int importid, int itemid) {
        this.importid = importid;
        this.itemid = itemid;
    }

    public int getImportid() {
        return importid;
    }

    public void setImportid(int importid) {
        this.importid = importid;
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
        hash += (int) importid;
        hash += (int) itemid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImportdetailPK)) {
            return false;
        }
        ImportdetailPK other = (ImportdetailPK) object;
        if (this.importid != other.importid) {
            return false;
        }
        if (this.itemid != other.itemid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.ImportdetailPK[ importid=" + importid + ", itemid=" + itemid + " ]";
    }

}
