package guru.springframework.sfgrestbrewery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import guru.springframework.sfgrestbrewery.model.Beer;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Integer>, BeerRepositoryCustom{}
