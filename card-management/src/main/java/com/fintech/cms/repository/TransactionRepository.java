package com.fintech.cms.repository;

import com.fintech.cms.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {}