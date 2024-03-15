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
@Table(name = "BILL")
@NamedQueries({
    @NamedQuery(name = "Bill.findAll", query = "SELECT b FROM Bill b"),
    @NamedQuery(name = "Bill.findByBillID", query = "SELECT b FROM Bill b WHERE b.billID = :billID"),
    @NamedQuery(name = "Bill.findByCreatedDate", query = "SELECT b FROM Bill b WHERE b.createdDate = :createdDate")})
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BillID")
    private Integer billID;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
    private Collection<BILLDetail> bILLDetailCollection;
    @JoinColumn(name = "CustomerId", referencedColumnName = "CustomerID")
    @ManyToOne(optional = false)
    private Customer customerId;
    @JoinColumn(name = "EMPLOYEEID", referencedColumnName = "EMPLOYEEID")
    @ManyToOne(optional = false)
    private Employees employeeid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "billid")
    private Collection<Itemstakeback> itemstakebackCollection;

    public Bill() {
    }

    public Bill(Integer billID) {
        this.billID = billID;
    }

    public Bill(Integer billID, Date createdDate) {
        this.billID = billID;
        this.createdDate = createdDate;
    }

    public Integer getBillID() {
        return billID;
    }

    public void setBillID(Integer billID) {
        this.billID = billID;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Collection<BILLDetail> getBILLDetailCollection() {
        return bILLDetailCollection;
    }

    public void setBILLDetailCollection(Collection<BILLDetail> bILLDetailCollection) {
        this.bILLDetailCollection = bILLDetailCollection;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Employees getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Employees employeeid) {
        this.employeeid = employeeid;
    }

    public Collection<Itemstakeback> getItemstakebackCollection() {
        return itemstakebackCollection;
    }

    public void setItemstakebackCollection(Collection<Itemstakeback> itemstakebackCollection) {
        this.itemstakebackCollection = itemstakebackCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (billID != null ? billID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bill)) {
            return false;
        }
        Bill other = (Bill) object;
        if ((this.billID == null && other.billID != null) || (this.billID != null && !this.billID.equals(other.billID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Bill[ billID=" + billID + " ]";
    }

}
