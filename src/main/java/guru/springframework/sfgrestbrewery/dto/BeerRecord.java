package guru.springframework.sfgrestbrewery.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import guru.springframework.sfgrestbrewery.model.BeerStyleEnum;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonSerialize
public record BeerRecord(Integer id, Long version, 
		String beerName, BeerStyleEnum beerStyle, String upc, 
		Integer quantityOnHand, BigDecimal price, @JsonFormat
		(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss") Date createdDate, @JsonFormat
		(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss") Date lastModifiedDate) {

}
