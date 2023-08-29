package guru.springframework.sfgrestbrewery.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import guru.springframework.sfgrestbrewery.dto.BeerRecord;
import guru.springframework.sfgrestbrewery.model.Beer;

@Mapper
public interface BeerMapper {
    List<BeerRecord> mapToDtos(List<Beer> beers);
    List<Beer> map(List<BeerRecord> beerDtos);
}

