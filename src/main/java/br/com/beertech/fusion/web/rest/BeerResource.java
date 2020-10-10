package br.com.beertech.fusion.web.rest;

import br.com.beertech.fusion.domain.Beer;
import br.com.beertech.fusion.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/beer")
public class BeerResource {

    @Autowired
    private BeerService beerService;

    @GetMapping("/listAll")
    public List<Beer> listAllBeer() {
        return beerService.listAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Beer> saveBeer(@RequestBody Beer beer) {
        return new ResponseEntity<>(beerService.saveOrUpdate(beer), CREATED);
    }

    @DeleteMapping("{id}")
    public void deleteBeer(@PathVariable Long id) {
        beerService.delete(id);
    }

}
