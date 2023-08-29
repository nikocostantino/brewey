package guru.springframework.sfgrestbrewery.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import guru.springframework.sfgrestbrewery.model.Beer;
import guru.springframework.sfgrestbrewery.model.BeerStyleEnum;

public class BeerRepositoryImpl implements BeerRepositoryCustom{
    
    @Autowired
	private JdbcTemplate jdbcTemplate;

    public List<Beer> findBeersByParams(String beerName, String upc, BeerStyleEnum beerStyle) {
		
		String sql = "SELECT * FROM beers WHERE ";
		
		if(beerName != null) {
			sql += "beer_name LIKE '%" + beerName +"%' AND ";
		}
		if(upc != null) {
			sql += "upc = " + "'" + upc + "'" + " AND ";
		}
		if(beerStyle != null) {
			sql += "beer_style = " + "'" + beerStyle.name() + "'";
		}
		
		return jdbcTemplate.query(sql.endsWith("AND ")?sql.replace("AND ", ""):sql, 
				(rs, rowNum) -> new Beer(
						rs.getInt("beer_id"),
						rs.getLong("version"),
						rs.getString("beer_name"),
						BeerStyleEnum.valueOf(rs.getString("beer_style")),
						rs.getString("upc"),
						rs.getInt("quantity_on_hand"),
						rs.getBigDecimal("price"),
						rs.getDate("created_date"),
						rs.getDate("last_modified_date")
				));
	}
}
