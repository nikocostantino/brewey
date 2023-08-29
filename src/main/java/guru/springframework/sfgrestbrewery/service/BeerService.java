package guru.springframework.sfgrestbrewery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.sfgrestbrewery.model.Beer;
import guru.springframework.sfgrestbrewery.model.BeerStyleEnum;
import guru.springframework.sfgrestbrewery.repository.BeerRepository;

@Service
public class BeerService {
	
	@Autowired
	private BeerRepository beerRepository;
	
	public Beer save(Beer beer) {
		return beerRepository.save(beer);
	}
	
	public long getBeerCount() {
		return beerRepository.count();
	}
	
	public List<Beer> getAllBeers(){
		return beerRepository.findAll();
	}
	
	public Optional<Beer> getBeerById(final Integer beerId){
		return beerRepository.findById(beerId);
	}
    
    public void deleteBeerById(Integer beerId) {
    	beerRepository.deleteById(beerId);
    }

	public List<Beer>findBeersByParams(String beerName, String upc, BeerStyleEnum beerStyle){
		return beerRepository.findBeersByParams(beerName, upc, beerStyle);
	}

}
