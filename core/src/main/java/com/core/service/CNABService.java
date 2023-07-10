package com.core.service;

import com.core.entities.CNAB;
import com.core.entities.FIleCNAB;
import com.core.entities.Transactions_CNAB;
import com.core.entities.User;
import com.core.repositories.CNABFileRepository;
import com.core.repositories.CNABRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CNABService {

    @Autowired
    private CNABFileRepository cNABFileRepository;

    @Autowired
    private CNABRepository cNABRepository;

    @Autowired
    private OauthService oauthService;


    public FIleCNAB salveDataCNABFile(MultipartFile file, String token) throws IOException {
        try {
            User user = this.oauthService.getUserByToken(token);

            FIleCNAB fIleCNAB = this.cNABFileRepository.save(FIleCNAB.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .fileData(file.getBytes()).build());



            this.processFileCNAB(file, fIleCNAB.getId(), user);

            return fIleCNAB;

        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR process File CNAB failed: ");
        }
    }

    private void processFileCNAB(MultipartFile file, UUID fIleCNAB, User user){
        log.info("hohoho");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            List<CNAB> cnabs = new ArrayList<CNAB>();
            String line;
            while ((line = bufferedReader.readLine()) != null){
                log.info(line);
                cnabs.add(this.processLineFileCNAB(line, fIleCNAB, user));
            }

            this.salveDataCNAB(cnabs);

        }catch (IOException e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR process File CNAB failed: ");
        }
    }

    private CNAB processLineFileCNAB(String line, UUID fIleCNAB, User user){
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

            double value = Double.parseDouble(line.substring(9, 19)) / 100.00;
            return CNAB.builder()
                    .cpf(line.substring(19, 30))
                    .date(LocalDate.parse(line.substring(1, 9), dateFormatter))
                    .value(value)
                    .card(line.substring(30, 42))
                    .time(LocalTime.parse(line.substring(42, 48), timeFormatter))
                    .storeName(line.substring(62).trim())
                    .storeOwner(line.substring(48, 62).trim())
                    .fIleCNAB(FIleCNAB.builder().id(fIleCNAB).build())
                    .Transactions_CNAB( Transactions_CNAB.builder().id(Long.valueOf(line.substring(0, 1))).build())
                    .user(user)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR process File CNAB failed: ");
        }
    }

    @Transactional
    private void salveDataCNAB(List<CNAB> cnabs){
        try {
            cNABRepository.saveAll(cnabs);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR process File CNAB failed: ");
        }
    }


    public List<CNAB> getSells(String token) throws IOException {
        try {
            User user = this.oauthService.getUserByToken(token);
            List<CNAB> cnab = cNABRepository.findAllByUser(user);

            cnab.forEach( cnab1 -> {
                cnab1.setUser(user);
                cnab1.setFIleCNAB(FIleCNAB.builder().id(cnab1.getFIleCNAB().getId()).build());
                    });

            return cnab;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR process File CNAB failed: ");
        }
    }

    public List<CNAB> getSellsStoreName(String token) throws IOException {
        try {
            User user = this.oauthService.getUserByToken(token);
            List<CNAB> cnab = cNABRepository.findAllByUser(user);

            cnab.forEach( cnab1 -> {
                cnab1.setUser(user);
                cnab1.setFIleCNAB(FIleCNAB.builder().id(cnab1.getFIleCNAB().getId()).build());
            });

            return cnab;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ERROR process File CNAB failed: ");
        }
    }
}
