package com.core.repositories;

import com.core.entities.CNAB;
import com.core.entities.FIleCNAB;
import com.core.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CNABRepository extends JpaRepository<CNAB, UUID> {

    List<CNAB> findAllByUser(User user);
}
