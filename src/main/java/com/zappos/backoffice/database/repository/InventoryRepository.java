package com.zappos.backoffice.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zappos.backoffice.database.model.Inventory;
import com.zappos.backoffice.database.model.InventoryId;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, InventoryId>, JpaSpecificationExecutor<Inventory>{
}
