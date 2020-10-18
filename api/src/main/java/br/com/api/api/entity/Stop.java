package br.com.api.api.entity;

//import java.util.Objects;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Stop {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Route route;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(nullable=false)
    private String description;

    @Column(nullable=false)
    private Integer deliveryRadius;


    public Route getRoute() {
        return this.route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeliveryRadius() {
        return this.deliveryRadius;
    }

    public void setDeliveryRadius(int deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    @Override
    public String toString() {
        return "{" +
            " id=" + getId()+
            ", lat=" + getLat()+
            ", lng=" + getLng()+
            ", description=" + getDescription()+
            ", deliveryRadius=" + getDeliveryRadius()+
            "}";
    }


}
