package com.example.eshop.service.impl;

import com.example.eshop.domain.entity.Check;
import com.example.eshop.domain.entity.Order;
import com.example.eshop.domain.entity.OrderItem;
import com.example.eshop.domain.entity.Product;
import com.example.eshop.enums.OrderState;
import com.example.eshop.dto.request.OrderRequest;
import com.example.eshop.dto.request.ProductRequest;

import com.example.eshop.dto.response.CheckResponse;
import com.example.eshop.exception.GlobalEShopException;
import com.example.eshop.mapper.CheckMapper;
import com.example.eshop.rabbit.producer.RabbitProducer;
import com.example.eshop.repository.CheckRepository;
import com.example.eshop.repository.OrderItemsRepository;
import com.example.eshop.repository.OrderRepository;
import com.example.eshop.service.OrderService;
import com.example.eshop.service.ProductService;
import com.example.eshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CheckRepository checkRepository;
    private final RabbitProducer rabbitProducer;
    private final CheckMapper checkMapper;
    private final OrderItemsRepository orderItemsRepository;

    @Override
    public CheckResponse orderProduct(long userId, OrderRequest orderRequest) {
        List<ProductRequest> productRequestList = orderRequest.getProductList();
        if (productRequestList.isEmpty())

            throw new GlobalEShopException("There are no any products");
        userService.findById(userId);

        Order parentOrder = Order.builder()
                .userId(userId)
                .build();

        parentOrder = orderRepository.save(parentOrder);
        double sum = 0;

        List<Product> productList = new LinkedList<>();
        List<OrderItem> orderItemsList = new LinkedList<>();
        for (ProductRequest productRequest : productRequestList){
            long productId = productRequest.getId();
            int productRequestAmount = productRequest.getAmount();
            Product product = productService
                    .findById(productId);
            int productAmount = product.getAmount();
            if (productRequestAmount>product.getAmount()){
                parentOrder.setOrderState(OrderState.CANCELLED);
                orderRepository.save(parentOrder);
                throw new GlobalEShopException("Insufficient product amount");
            }

            orderItemsList.add(OrderItem.builder()
                    .productId(productId)
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .userId(userId)
                    .parentOrderId(parentOrder.getId())
                    .orderedProductAmount(productRequestAmount)
                    .build());
            productAmount-= productRequestAmount;
            product.setAmount(productAmount);
            productList.add(product);
            sum+=product.getPrice()*productRequestAmount;
        }

        productService.saveAll(productList);

        parentOrder.setOrderState(OrderState.PENDING);
        orderRepository.save(parentOrder);

        Check check = Check.builder()
                .orderId(parentOrder.getId())
                .userId(userId)
                .totalSum(sum)
                .build();
        orderItemsRepository.saveAll(orderItemsList);

        rabbitProducer.sendOrderMessage("""
                ***********************************
                
                Order items are in payment process
                
                ***********************************""");

        return checkMapper.toDto(checkRepository.save(check));
    }


    @Override
    public Order findById(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new GlobalEShopException(String.format(
                        "Order not found by this id --> %s", orderId)));
    }


    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public String cancelOrder(long userId, long orderId) {
        userService.findById(userId);
        Order order = findById(orderId);
        if (order.getUserId()!=userId)
            throw new GlobalEShopException("User id is not equal with Order user id");

        if (order.getOrderState().equals(OrderState.ORDERED)
                ||order.getOrderState().equals(OrderState.CANCELLED))
            throw new GlobalEShopException(String.format(
                    "Order state is not in PENDING state! order state-->%s", order.getOrderState().name()));


        List<Product> productList = new LinkedList<>();
        List<OrderItem> orderItemList = orderItemsRepository.findByParentOrderId(orderId);
        for (OrderItem orderItem : orderItemList) {
            Product product = productService.findById(orderItem.getProductId());
            product.setAmount(product.getAmount()+orderItem.getOrderedProductAmount());
            productList.add(product);
        }

        order.setOrderState(OrderState.CANCELLED);
        productService.saveAll(productList);
        orderRepository.save(order);
        rabbitProducer.sendOrderMessage("""
                ***********************************
                
                Order has been cancelled
                
                ***********************************
                """);
        return "Order has been cancelled";
    }
}
