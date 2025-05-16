package com.Od_Tasking.repository;

import com.Od_Tasking.entity.Orders.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends MongoRepository<Order,String> {

}
