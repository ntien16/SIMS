/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.sims.entitynew;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
@Entity
@Table(name = "DELETEBILL")
@NamedQueries({
    @NamedQuery(name = "Deletebill.findAll", query = "SELECT d FROM Deletebill d"),
    @NamedQuery(name = "Deletebill.findByDeletebillid", query = "SELECT d FROM Deletebill d WHERE d.deletebillid = :deletebillid"),
    @NamedQuery(name = "Deletebill.findByBillid", query = "SELECT d FROM Deletebill d WHERE d.billid = :billid"),
    @NamedQuery(name = "Deletebill.findByDeletedate", query = "SELECT d FROM Deletebill d WHERE d.deletedate = :deletedate"),
    @NamedQuery(name = "Deletebill.findByStatuz", query = "SELECT d FROM Deletebill d WHERE d.statuz = :statuz")})
public class Deletebill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DELETEBILLID")
    private Integer deletebillid;
    @Basic(optional = false)
    @Column(name = "BILLID")
    private int billid;
    @Column(name = "DELETEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedate;
    @Column(name = "statuz")
    private String statuz;

    public Deletebill() {
    }

    public Deletebill(Integer deletebillid) {
        this.deletebillid = deletebillid;
    }

    public Deletebill(Integer deletebillid, int billid) {
        this.deletebillid = deletebillid;
        this.billid = billid;
    }

    public Integer getDeletebillid() {
        return deletebillid;
    }

    public void setDeletebillid(Integer deletebillid) {
        this.deletebillid = deletebillid;
    }

    public int getBillid() {
        return billid;
    }

    public void setBillid(int billid) {
        this.billid = billid;
    }

    public Date getDeletedate() {
        return deletedate;
    }

    public void setDeletedate(Date deletedate) {
        this.deletedate = deletedate;
    }

    public String getStatuz() {
        return statuz;
    }

    public void setStatuz(String statuz) {
        this.statuz = statuz;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deletebillid != null ? deletebillid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deletebill)) {
            return false;
        }
        Deletebill other = (Deletebill) object;
        if ((this.deletebillid == null && other.deletebillid != null) || (this.deletebillid != null && !this.deletebillid.equals(other.deletebillid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Deletebill[ deletebillid=" + deletebillid + " ]";
    }

}
