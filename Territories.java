/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "territories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Territories.findAll", query = "SELECT t FROM Territories t")
    , @NamedQuery(name = "Territories.findByTerritoryID", query = "SELECT t FROM Territories t WHERE t.territoryID = :territoryID")
    , @NamedQuery(name = "Territories.findByTerritoryDescription", query = "SELECT t FROM Territories t WHERE t.territoryDescription = :territoryDescription")})
public class Territories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TerritoryID")
    private String territoryID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TerritoryDescription")
    private String territoryDescription;
    @ManyToMany(mappedBy = "territoriesList")
    private List<Employees> employeesList;
    @JoinColumn(name = "RegionID", referencedColumnName = "RegionID")
    @ManyToOne(optional = false)
    private Region regionID;

    public Territories() {
    }

    public Territories(String territoryID) {
        this.territoryID = territoryID;
    }

    public Territories(String territoryID, String territoryDescription) {
        this.territoryID = territoryID;
        this.territoryDescription = territoryDescription;
    }

    public String getTerritoryID() {
        return territoryID;
    }

    public void setTerritoryID(String territoryID) {
        this.territoryID = territoryID;
    }

    public String getTerritoryDescription() {
        return territoryDescription;
    }

    public void setTerritoryDescription(String territoryDescription) {
        this.territoryDescription = territoryDescription;
    }

    @XmlTransient
    public List<Employees> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(List<Employees> employeesList) {
        this.employeesList = employeesList;
    }

    public Region getRegionID() {
        return regionID;
    }

    public void setRegionID(Region regionID) {
        this.regionID = regionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (territoryID != null ? territoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Territories)) {
            return false;
        }
        Territories other = (Territories) object;
        if ((this.territoryID == null && other.territoryID != null) || (this.territoryID != null && !this.territoryID.equals(other.territoryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Territories[ territoryID=" + territoryID + " ]";
    }
    
}
