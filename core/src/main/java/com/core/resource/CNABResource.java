package com.core.resource;

import com.core.entities.CNAB;
import com.core.entities.FIleCNAB;
import com.core.service.CNABService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/file")
public class CNABResource {

    @Autowired
    CNABService cBNAService;

    @PostMapping
    public ResponseEntity<FIleCNAB> create(@RequestParam("file") MultipartFile file,
        @RequestHeader(value = "Authorization") String token) throws IOException {
        return ResponseEntity.ok(this.cBNAService.salveDataCNABFile(file, token));
    }

    @GetMapping
    public ResponseEntity<List<CNAB>> getSells(@RequestHeader(value = "Authorization") String token) throws IOException {
        return ResponseEntity.ok(this.cBNAService.getSells(token));
    }

    @GetMapping(value = "/storeName")
    public ResponseEntity<List<CNAB>> getSellsStoreName(@RequestHeader(value = "Authorization") String token) throws IOException {
        return ResponseEntity.ok(this.cBNAService.getSellsStoreName(token));
    }
}
