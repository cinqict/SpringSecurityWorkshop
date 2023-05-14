package com.cinqict.workshop.controller;

import com.cinqict.workshop.domain.LegoBoxset;
import com.cinqict.workshop.service.LegoBoxsetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lego")
public class LegoSetController {

    private final LegoBoxsetService legoBoxsetService;

    @PostMapping
    public LegoBoxset create(@RequestBody final LegoBoxset legoBoxset) {
        return legoBoxsetService.create(legoBoxset);
    }

    @GetMapping("/{serialNr}")
    public LegoBoxset retrieve(@PathVariable final String serialNr) {
        return legoBoxsetService.retrieve(serialNr)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
    }

    @GetMapping("")
    public List<LegoBoxset> retrieveAll() {
        return legoBoxsetService.retrieveAll();
    }

    @PutMapping
    public LegoBoxset update(@RequestBody final LegoBoxset legoBoxset) {
        return legoBoxsetService.update(legoBoxset);
    }

    @DeleteMapping("/{serialNr}")
    public void delete(@PathVariable final String serialNr) {
        legoBoxsetService.delete(serialNr);
    }
}
