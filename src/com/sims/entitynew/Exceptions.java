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
@Table(name = "Exceptions")
@NamedQueries({
    @NamedQuery(name = "Exceptions.findAll", query = "SELECT e FROM Exceptions e"),
    @NamedQuery(name = "Exceptions.findByExceptionId", query = "SELECT e FROM Exceptions e WHERE e.exceptionId = :exceptionId"),
    @NamedQuery(name = "Exceptions.findByExceptionDate", query = "SELECT e FROM Exceptions e WHERE e.exceptionDate = :exceptionDate")})
public class Exceptions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ExceptionId")
    private Integer exceptionId;
    @Basic(optional = false)
    @Column(name = "ExceptionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exceptionDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exceptions")
    private Collection<Exceptionsdetail> exceptionsdetailCollection;
    @JoinColumn(name = "EMPLOYEEID", referencedColumnName = "EMPLOYEEID")
    @ManyToOne(optional = false)
    private Employees employeeid;

    public Exceptions() {
    }

    public Exceptions(Integer exceptionId) {
        this.exceptionId = exceptionId;
    }

    public Exceptions(Integer exceptionId, Date exceptionDate) {
        this.exceptionId = exceptionId;
        this.exceptionDate = exceptionDate;
    }

    public Integer getExceptionId() {
        return exceptionId;
    }

    public void setExceptionId(Integer exceptionId) {
        this.exceptionId = exceptionId;
    }

    public Date getExceptionDate() {
        return exceptionDate;
    }

    public void setExceptionDate(Date exceptionDate) {
        this.exceptionDate = exceptionDate;
    }

    public Collection<Exceptionsdetail> getExceptionsdetailCollection() {
        return exceptionsdetailCollection;
    }

    public void setExceptionsdetailCollection(Collection<Exceptionsdetail> exceptionsdetailCollection) {
        this.exceptionsdetailCollection = exceptionsdetailCollection;
    }

    public Employees getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Employees employeeid) {
        this.employeeid = employeeid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exceptionId != null ? exceptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exceptions)) {
            return false;
        }
        Exceptions other = (Exceptions) object;
        if ((this.exceptionId == null && other.exceptionId != null) || (this.exceptionId != null && !this.exceptionId.equals(other.exceptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Exceptions[ exceptionId=" + exceptionId + " ]";
    }

}
