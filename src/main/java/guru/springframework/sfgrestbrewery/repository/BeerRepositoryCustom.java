package guru.springframework.sfgrestbrewery.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import guru.springframework.sfgrestbrewery.model.Beer;
import guru.springframework.sfgrestbrewery.model.BeerStyleEnum;

@Repository
public interface BeerRepositoryCustom{

    public List<Beer> findBeersByParams(String beerName, String upc, BeerStyleEnum beerStyle);
    
}
