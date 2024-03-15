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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "EMPLOYEES")
@NamedQueries({
    @NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e"),
    @NamedQuery(name = "Employees.findByEmployeeid", query = "SELECT e FROM Employees e WHERE e.employeeid = :employeeid"),
    @NamedQuery(name = "Employees.findByEmployeename", query = "SELECT e FROM Employees e WHERE e.employeename = :employeename"),
    @NamedQuery(name = "Employees.findByPassword", query = "SELECT e FROM Employees e WHERE e.password = :password"),
    @NamedQuery(name = "Employees.findByPhone", query = "SELECT e FROM Employees e WHERE e.phone = :phone"),
    @NamedQuery(name = "Employees.findByAddress", query = "SELECT e FROM Employees e WHERE e.address = :address")})
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "EMPLOYEEID")
    private Integer employeeid;
    @Basic(optional = false)
    @Column(name = "EMPLOYEENAME")
    private String employeename;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "ADDRESS")
    private String address;
    @OneToMany(mappedBy = "employeeid")
    private Collection<Report> reportCollection;
    @JoinColumn(name = "ROLEID", referencedColumnName = "ROLEID")
    @ManyToOne
    private Rolez roleid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeid")
    private Collection<Importitems> importitemsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeid")
    private Collection<Bill> billCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeid")
    private Collection<Exceptions> exceptionsCollection;

    public Employees() {
    }

    public Employees(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Employees(Integer employeeid, String employeename, String password) {
        this.employeeid = employeeid;
        this.employeename = employeename;
        this.password = password;
    }

    public Employees(String employeename, String password, String phone, String address, Rolez roleid) {
        this.employeename = employeename;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.roleid = roleid;
    }
    
    
    
    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection<Report> getReportCollection() {
        return reportCollection;
    }

    public void setReportCollection(Collection<Report> reportCollection) {
        this.reportCollection = reportCollection;
    }

    public Rolez getRoleid() {
        return roleid;
    }

    public void setRoleid(Rolez roleid) {
        this.roleid = roleid;
    }

    public Collection<Importitems> getImportitemsCollection() {
        return importitemsCollection;
    }

    public void setImportitemsCollection(Collection<Importitems> importitemsCollection) {
        this.importitemsCollection = importitemsCollection;
    }

    public Collection<Bill> getBillCollection() {
        return billCollection;
    }

    public void setBillCollection(Collection<Bill> billCollection) {
        this.billCollection = billCollection;
    }

    public Collection<Exceptions> getExceptionsCollection() {
        return exceptionsCollection;
    }

    public void setExceptionsCollection(Collection<Exceptions> exceptionsCollection) {
        this.exceptionsCollection = exceptionsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeid != null ? employeeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.employeeid == null && other.employeeid != null) || (this.employeeid != null && !this.employeeid.equals(other.employeeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Employees[ employeeid=" + employeeid + " ]";
    }

}
