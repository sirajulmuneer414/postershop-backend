package com.postershop.backend.repository;

import com.postershop.backend.entity.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ShopOrder, Long> {

    List<ShopOrder> findByUserUsername(String username);


}
