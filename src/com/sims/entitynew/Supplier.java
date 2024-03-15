/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.entitynew;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "SUPPLIER")
@NamedQueries({
    @NamedQuery(name = "Supplier.findAll", query = "SELECT s FROM Supplier s"),
    @NamedQuery(name = "Supplier.findBySupplierid", query = "SELECT s FROM Supplier s WHERE s.supplierid = :supplierid"),
    @NamedQuery(name = "Supplier.findBySuppilerName", query = "SELECT s FROM Supplier s WHERE s.suppilerName = :suppilerName"),
    @NamedQuery(name = "Supplier.findBySuppilerphone", query = "SELECT s FROM Supplier s WHERE s.suppilerphone = :suppilerphone"),
    @NamedQuery(name = "Supplier.findBySuppilerAddress", query = "SELECT s FROM Supplier s WHERE s.suppilerAddress = :suppilerAddress")})
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "supplierid")
    private Integer supplierid;
    @Basic(optional = false)
    @Column(name = "suppilerName")
    private String suppilerName;
    @Basic(optional = false)
    @Column(name = "suppilerphone")
    private String suppilerphone;
    @Column(name = "suppilerAddress")
    private String suppilerAddress;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierid")
    private Collection<Importitems> importitemsCollection;

    public Supplier() {
    }

    public Supplier(Integer supplierid) {
        this.supplierid = supplierid;
    }

    public Supplier(Integer supplierid, String suppilerName, String suppilerphone) {
        this.supplierid = supplierid;
        this.suppilerName = suppilerName;
        this.suppilerphone = suppilerphone;
    }

    public Supplier(Integer supplierid, String suppilerName, String suppilerphone, String suppilerAddress) {
        this.supplierid = supplierid;
        this.suppilerName = suppilerName;
        this.suppilerphone = suppilerphone;
        this.suppilerAddress = suppilerAddress;
    }

    public Supplier(String suppilerName, String suppilerphone, String suppilerAddress) {
        this.suppilerName = suppilerName;
        this.suppilerphone = suppilerphone;
        this.suppilerAddress = suppilerAddress;
    }

    
    
    public Integer getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(Integer supplierid) {
        this.supplierid = supplierid;
    }

    public String getSuppilerName() {
        return suppilerName;
    }

    public void setSuppilerName(String suppilerName) {
        this.suppilerName = suppilerName;
    }

    public String getSuppilerphone() {
        return suppilerphone;
    }

    public void setSuppilerphone(String suppilerphone) {
        this.suppilerphone = suppilerphone;
    }

    public String getSuppilerAddress() {
        return suppilerAddress;
    }

    public void setSuppilerAddress(String suppilerAddress) {
        this.suppilerAddress = suppilerAddress;
    }

    public Collection<Importitems> getImportitemsCollection() {
        return importitemsCollection;
    }

    public void setImportitemsCollection(Collection<Importitems> importitemsCollection) {
        this.importitemsCollection = importitemsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierid != null ? supplierid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supplier)) {
            return false;
        }
        Supplier other = (Supplier) object;
        if ((this.supplierid == null && other.supplierid != null) || (this.supplierid != null && !this.supplierid.equals(other.supplierid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Supplier[ supplierid=" + supplierid + " ]";
    }

}
