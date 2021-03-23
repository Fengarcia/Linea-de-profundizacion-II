/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "region")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Region.findAll", query = "SELECT r FROM Region r")
    , @NamedQuery(name = "Region.findByRegionID", query = "SELECT r FROM Region r WHERE r.regionID = :regionID")
    , @NamedQuery(name = "Region.findByRegionDescription", query = "SELECT r FROM Region r WHERE r.regionDescription = :regionDescription")})
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RegionID")
    private Integer regionID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "RegionDescription")
    private String regionDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regionID")
    private List<Territories> territoriesList;

    public Region() {
    }

    public Region(Integer regionID) {
        this.regionID = regionID;
    }

    public Region(Integer regionID, String regionDescription) {
        this.regionID = regionID;
        this.regionDescription = regionDescription;
    }

    public Integer getRegionID() {
        return regionID;
    }

    public void setRegionID(Integer regionID) {
        this.regionID = regionID;
    }

    public String getRegionDescription() {
        return regionDescription;
    }

    public void setRegionDescription(String regionDescription) {
        this.regionDescription = regionDescription;
    }

    @XmlTransient
    public List<Territories> getTerritoriesList() {
        return territoriesList;
    }

    public void setTerritoriesList(List<Territories> territoriesList) {
        this.territoriesList = territoriesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionID != null ? regionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
        if ((this.regionID == null && other.regionID != null) || (this.regionID != null && !this.regionID.equals(other.regionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Region[ regionID=" + regionID + " ]";
    }
    
}
