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
@Table(name = "IMPORTITEMS")
@NamedQueries({
    @NamedQuery(name = "Importitems.findAll", query = "SELECT i FROM Importitems i"),
    @NamedQuery(name = "Importitems.findByImportid", query = "SELECT i FROM Importitems i WHERE i.importid = :importid"),
    @NamedQuery(name = "Importitems.findByImportdate", query = "SELECT i FROM Importitems i WHERE i.importdate = :importdate")})
public class Importitems implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Importid")
    private Integer importid;
    @Basic(optional = false)
    @Column(name = "importdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date importdate;
    @JoinColumn(name = "EMPLOYEEID", referencedColumnName = "EMPLOYEEID")
    @ManyToOne(optional = false)
    private Employees employeeid;
    @JoinColumn(name = "SUPPLIERID", referencedColumnName = "supplierid")
    @ManyToOne(optional = false)
    private Supplier supplierid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "importitems")
    private Collection<Importdetail> importdetailCollection;

    public Importitems() {
    }

    public Importitems(Integer importid) {
        this.importid = importid;
    }

    public Importitems(Integer importid, Date importdate) {
        this.importid = importid;
        this.importdate = importdate;
    }

    public Integer getImportid() {
        return importid;
    }

    public void setImportid(Integer importid) {
        this.importid = importid;
    }

    public Date getImportdate() {
        return importdate;
    }

    public void setImportdate(Date importdate) {
        this.importdate = importdate;
    }

    public Employees getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Employees employeeid) {
        this.employeeid = employeeid;
    }

    public Supplier getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Supplier supplierid) {
        this.supplierid = supplierid;
    }

    public Collection<Importdetail> getImportdetailCollection() {
        return importdetailCollection;
    }

    public void setImportdetailCollection(Collection<Importdetail> importdetailCollection) {
        this.importdetailCollection = importdetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (importid != null ? importid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Importitems)) {
            return false;
        }
        Importitems other = (Importitems) object;
        if ((this.importid == null && other.importid != null) || (this.importid != null && !this.importid.equals(other.importid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Importitems[ importid=" + importid + " ]";
    }

}
