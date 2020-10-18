package br.com.api.api.entity;

import javax.persistence.*;
import java.util.HashSet;  
import java.util.Set;

@Entity
public class Route
{
    
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //private long id;
    @Id
    @Column(nullable = false)
    private String routeKey;
    
    @Column(nullable=false)
    private String assignedVehicle;

    @Column(nullable=false)
    private String status;

    @OneToMany(mappedBy = "route",cascade = CascadeType.ALL)
    private Set<Stop> plannedStops= new HashSet<>();;

/*
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
*/
    public String getRouteKey() {
        return this.routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public String getAssignedVehicle() {
        return this.assignedVehicle;
    }

    public void setAssignedVehicle(String assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Stop> getStops() {
        return this.plannedStops;
    }

    public void setStops(Set<Stop> plannedStops) {
        this.plannedStops = plannedStops;
        for(Stop s : plannedStops) {
            s.setRoute(this);
        }
    }

    @Override
    public String toString() {
        return "{" +
            " routeKey=" + getRouteKey()+
            ", assignedVehicle=" + getAssignedVehicle() +
            ", status=" + getStatus() +
            "}";
    }
    
}