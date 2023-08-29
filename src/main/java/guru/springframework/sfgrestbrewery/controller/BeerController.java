package guru.springframework.sfgrestbrewery.controller;

import java.util.List;
import java.util.stream.StreamSupport;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.sfgrestbrewery.dto.BeerRecord;
import guru.springframework.sfgrestbrewery.exception.BeerNotFoundException;
import guru.springframework.sfgrestbrewery.mapper.BeerMapper;
import guru.springframework.sfgrestbrewery.model.Beer;
import guru.springframework.sfgrestbrewery.model.BeerStyleEnum;
import guru.springframework.sfgrestbrewery.service.BeerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/v1/")
@RestController
@ResponseBody
@Slf4j
public class BeerController {
	
	@Autowired
	private BeerService beerService;
	private BeerMapper beerMapper = Mappers.getMapper(BeerMapper.class);
	
	@GetMapping(value = "beers")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<CollectionModel<Beer>> getBeers(){
		log.info("Getting all beers from the Database");
		final Iterable<Beer> beerIterable = beerService.getAllBeers();
		final List<Beer> beers = StreamSupport
				.stream(beerIterable.spliterator(), false)
				.toList();
		for(Beer beer : beers) {
			buildBeerHateoas(beer);
		}
		Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BeerController.class).getBeers()).withSelfRel();
		return new ResponseEntity<>(CollectionModel.of(beers, link), HttpStatus.OK);
	}
	
	@GetMapping(value = "beers/{beerId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Beer> getBeerById(@PathVariable(name = "beerId") final Integer beerId){
		log.info("Getting beer with beerId {} from the Database.", beerId);
		final Beer beer = beerService.getBeerById(beerId)
				.orElseThrow(() -> new BeerNotFoundException
					("Beer with beerId " + beerId 
							+ " not found in the Database"));
		buildBeerHateoas(beer);					
		return new ResponseEntity<>(beer, HttpStatus.OK);
	}
	
	@GetMapping(value = "beers/beers-from-query")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<BeerRecord>> getBeersFromQueryParams(
			@RequestParam(value = "beerName", required = false) String beerName,
			@RequestParam(value = "upc", required = false) String upc,
			@RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle){
		log.info("Getting beer(s) with these values: {}, {}, {} "
				+ "from the Database", beerName, upc, beerStyle);
		final List<BeerRecord> beersByParams = beerMapper.mapToDtos(beerService.findBeersByParams(beerName, upc, beerStyle));
		return new ResponseEntity<>(beersByParams, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Create or update a beer")
	@PostMapping(value = "beers/beer")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Beer> saveNewBeer(@RequestBody Beer beer){
		log.info("Saving new beer in the Database");
		Beer savedBeer = beerService.save(beer);
		return new ResponseEntity<Beer>(savedBeer, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "beers/{beerId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> deleteBeerById(@PathVariable(name = "beerId") Integer beerId){
		log.info("Deleting beer with beerId {} from the Database", beerId);
		beerService.deleteBeerById(beerId);
		log.info("Record with beerId {} has been deleted", beerId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void buildBeerHateoas(Beer beer) {
		Link beerLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(BeerController.class)
						.getBeerById(beer.getId()))
				.withRel("beer-id");
		Link updateBeerLink = WebMvcLinkBuilder
		.linkTo(WebMvcLinkBuilder
				.methodOn(BeerController.class)
				.saveNewBeer(beer))
		.withRel("beer-id");					
		Link deleteBeerLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(BeerController.class)
						.deleteBeerById(beer.getId()))
				.withRel("delete-beer");
		beer.add(beerLink);
		beer.add(updateBeerLink);
		beer.add(deleteBeerLink);
	}

}
