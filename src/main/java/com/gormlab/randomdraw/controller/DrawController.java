package com.gormlab.randomdraw.controller;

import com.gormlab.randomdraw.model.WeightedEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class DrawController {

    @Autowired
    DrawMaster drawmaster;

    @PostMapping(value="/draw", params = {"numberOfWinners", "seed"})
    @ResponseBody
    public ResponseEntity<Object> draw(@RequestBody List<WeightedEntry> entries, @RequestParam int numberOfWinners, @RequestParam int seed) {
        try {
            return ResponseEntity.ok(drawmaster.draw(entries, numberOfWinners, seed));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() != null ? e.getMessage() : "Malformed Request Body");
        }
    }

    @PostMapping(value="/draw",params = "numberOfWinners")
    @ResponseBody
    public ResponseEntity<Object> draw(@RequestBody List<WeightedEntry> entries, @RequestParam int numberOfWinners) {
        try {
            return ResponseEntity.ok(drawmaster.draw(entries, numberOfWinners));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() != null ? e.getMessage() : "Malformed Request Body");
        }
    }

    @PostMapping(value="/draw")
    @ResponseBody
    public ResponseEntity<Object> draw(@RequestBody List<WeightedEntry> entries) {
        try {
            return ResponseEntity.ok(drawmaster.draw(entries, 1));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage() != null ? e.getMessage() : "Malformed Request Body");
        }
    }
}
