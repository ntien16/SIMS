/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.entitynew;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "STATUZ")
@NamedQueries({
    @NamedQuery(name = "Statuz.findAll", query = "SELECT s FROM Statuz s"),
    @NamedQuery(name = "Statuz.findByStatusid", query = "SELECT s FROM Statuz s WHERE s.statusid = :statusid"),
    @NamedQuery(name = "Statuz.findByStatusname", query = "SELECT s FROM Statuz s WHERE s.statusname = :statusname")})
public class Statuz implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "STATUSID")
    private Integer statusid;
    @Basic(optional = false)
    @Column(name = "STATUSNAME")
    private String statusname;
    @OneToMany(mappedBy = "statusid")
    private Collection<Inventory> inventoryCollection;

    public Statuz() {
    }

    public Statuz(Integer statusid) {
        this.statusid = statusid;
    }

    public Statuz(Integer statusid, String statusname) {
        this.statusid = statusid;
        this.statusname = statusname;
    }

    public Integer getStatusid() {
        return statusid;
    }

    public void setStatusid(Integer statusid) {
        this.statusid = statusid;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public Collection<Inventory> getInventoryCollection() {
        return inventoryCollection;
    }

    public void setInventoryCollection(Collection<Inventory> inventoryCollection) {
        this.inventoryCollection = inventoryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusid != null ? statusid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Statuz)) {
            return false;
        }
        Statuz other = (Statuz) object;
        if ((this.statusid == null && other.statusid != null) || (this.statusid != null && !this.statusid.equals(other.statusid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Statuz[ statusid=" + statusid + " ]";
    }

}
