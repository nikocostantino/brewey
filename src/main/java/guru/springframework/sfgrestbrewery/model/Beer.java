package guru.springframework.sfgrestbrewery.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "Beers")
@NoArgsConstructor
@AllArgsConstructor
public class Beer extends RepresentationModel<Beer>{
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "beer_id")
    private Integer id;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "beer_name")
    private String beerName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "beer_style")
    private BeerStyleEnum beerStyle;
    
    @Column(name = "upc")
    private String upc;

    @Column(name = "quantity_on_hand")
    private Integer quantityOnHand;
    
    @Column(name = "price")
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    @JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createdDate;

    @UpdateTimestamp
    @JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date lastModifiedDate;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(beerName, beerStyle, createdDate, id, lastModifiedDate, price,
				quantityOnHand, upc, version);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Beer other = (Beer) obj;
		return Objects.equals(beerName, other.beerName) && beerStyle == other.beerStyle
				&& Objects.equals(createdDate, other.createdDate) && Objects.equals(id, other.id)
				&& Objects.equals(lastModifiedDate, other.lastModifiedDate) && Objects.equals(price, other.price)
				&& Objects.equals(quantityOnHand, other.quantityOnHand) && Objects.equals(upc, other.upc)
				&& Objects.equals(version, other.version);
	}
    
    

}
