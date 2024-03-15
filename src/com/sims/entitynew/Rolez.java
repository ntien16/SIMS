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
@Table(name = "ROLEZ")
@NamedQueries({
    @NamedQuery(name = "Rolez.findAll", query = "SELECT r FROM Rolez r"),
    @NamedQuery(name = "Rolez.findByRoleid", query = "SELECT r FROM Rolez r WHERE r.roleid = :roleid"),
    @NamedQuery(name = "Rolez.findByRolename", query = "SELECT r FROM Rolez r WHERE r.rolename = :rolename")})
public class Rolez implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROLEID")
    private Integer roleid;
    @Column(name = "ROLENAME")
    private String rolename;
    @OneToMany(mappedBy = "roleid")
    private Collection<Employees> employeesCollection;

    public Rolez() {
    }

    public Rolez(Integer roleid) {
        this.roleid = roleid;
    }

    public Rolez(Integer roleid, String rolename) {
        this.roleid = roleid;
        this.rolename = rolename;
    }

    
    
    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Collection<Employees> getEmployeesCollection() {
        return employeesCollection;
    }

    public void setEmployeesCollection(Collection<Employees> employeesCollection) {
        this.employeesCollection = employeesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleid != null ? roleid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolez)) {
            return false;
        }
        Rolez other = (Rolez) object;
        if ((this.roleid == null && other.roleid != null) || (this.roleid != null && !this.roleid.equals(other.roleid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Rolez[ roleid=" + roleid + " ]";
    }

}
