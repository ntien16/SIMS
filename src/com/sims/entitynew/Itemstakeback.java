/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.entitynew;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "ITEMSTAKEBACK")
@NamedQueries({
    @NamedQuery(name = "Itemstakeback.findAll", query = "SELECT i FROM Itemstakeback i"),
    @NamedQuery(name = "Itemstakeback.findByItemtkid", query = "SELECT i FROM Itemstakeback i WHERE i.itemtkid = :itemtkid"),
    @NamedQuery(name = "Itemstakeback.findByCanceldate", query = "SELECT i FROM Itemstakeback i WHERE i.canceldate = :canceldate")})
public class Itemstakeback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEMTKID")
    private Integer itemtkid;
    @Column(name = "CANCELDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date canceldate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemstakeback")
    private Collection<Itemstakebackdetail> itemstakebackdetailCollection;
    @JoinColumn(name = "BILLID", referencedColumnName = "BillID")
    @ManyToOne(optional = false)
    private Bill billid;
    @JoinColumn(name = "CUSTOMERID", referencedColumnName = "CustomerID")
    @ManyToOne(optional = false)
    private Customer customerid;

    public Itemstakeback() {
    }

    public Itemstakeback(Integer itemtkid) {
        this.itemtkid = itemtkid;
    }

    public Integer getItemtkid() {
        return itemtkid;
    }

    public void setItemtkid(Integer itemtkid) {
        this.itemtkid = itemtkid;
    }

    public Date getCanceldate() {
        return canceldate;
    }

    public void setCanceldate(Date canceldate) {
        this.canceldate = canceldate;
    }

    public Collection<Itemstakebackdetail> getItemstakebackdetailCollection() {
        return itemstakebackdetailCollection;
    }

    public void setItemstakebackdetailCollection(Collection<Itemstakebackdetail> itemstakebackdetailCollection) {
        this.itemstakebackdetailCollection = itemstakebackdetailCollection;
    }

    public Bill getBillid() {
        return billid;
    }

    public void setBillid(Bill billid) {
        this.billid = billid;
    }

    public Customer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Customer customerid) {
        this.customerid = customerid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemtkid != null ? itemtkid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemstakeback)) {
            return false;
        }
        Itemstakeback other = (Itemstakeback) object;
        if ((this.itemtkid == null && other.itemtkid != null) || (this.itemtkid != null && !this.itemtkid.equals(other.itemtkid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Itemstakeback[ itemtkid=" + itemtkid + " ]";
    }

}
