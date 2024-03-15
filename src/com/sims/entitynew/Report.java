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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "REPORT")
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findByReportid", query = "SELECT r FROM Report r WHERE r.reportid = :reportid"),
    @NamedQuery(name = "Report.findByContentreport", query = "SELECT r FROM Report r WHERE r.contentreport = :contentreport"),
    @NamedQuery(name = "Report.findByReportdate", query = "SELECT r FROM Report r WHERE r.reportdate = :reportdate")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REPORTID")
    private Integer reportid;
    @Column(name = "CONTENTREPORT")
    private String contentreport;
    @Basic(optional = false)
    @Column(name = "REPORTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportdate;
    @JoinColumn(name = "EMPLOYEEID", referencedColumnName = "EMPLOYEEID")
    @ManyToOne
    private Employees employeeid;

    public Report() {
    }

    public Report(Integer reportid) {
        this.reportid = reportid;
    }

    public Report(Integer reportid, Date reportdate) {
        this.reportid = reportid;
        this.reportdate = reportdate;
    }

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public String getContentreport() {
        return contentreport;
    }

    public void setContentreport(String contentreport) {
        this.contentreport = contentreport;
    }

    public Date getReportdate() {
        return reportdate;
    }

    public void setReportdate(Date reportdate) {
        this.reportdate = reportdate;
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
        hash += (reportid != null ? reportid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportid == null && other.reportid != null) || (this.reportid != null && !this.reportid.equals(other.reportid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.sims.entitynew.Report[ reportid=" + reportid + " ]";
    }

}
