package com.Od_Tasking.service.serviceImpl;


import com.Od_Tasking.entity.Orders.*;
//import com.Od_Tasking.entity.Orders.Properties;
import com.Od_Tasking.entity.User;
import com.Od_Tasking.repository.OrderRepository;
import com.Od_Tasking.repository.UserRepository;
import com.Od_Tasking.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thoughtworks.qdox.model.expression.Or;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final RestTemplate restTemplate;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public String getOrderById(Long id) throws IOException, InterruptedException {
        String url = "https://dev-api.geohub.vn/tasking/api/v1/orders/" + id;
        String cookies = "csrf_token_d00779a1cf3ae323ee7988811808dfd81402812b60cbb9b4c893b265e62b5269=fBpWgUZL2+V+Y8QckgYY3dF8fBwnMveR66wLYBSEMUg=;ory_kratos_session=MTc0NzM2MjM2MnxXVmtVUmt4SnhObTIyLUdobW11Q0FwTXF1MmhzcW5ZWWlHWlVKazkxMlh2ZEpMRkdWSDA3Vnh1LVhCWEVWTnZPS2VpS0NKSm9WQ25CSVJNLWFpYTI1ZklHejFwdzF4ODRrN2FWVFdiUGhUME15NjViSVhHZHRvN1pEZmQtdzd5ZjFNR3cydUxJbjE1YU5qUUVYMTJreDNmbGJIMFdjaWJ4VjdJaUM0SkZPemZmdU9yM0JKV21UQ3VZTFZERkN5b3FGUzE1S2k1OE9GcUtRSl9kdF9XU2hhb2pNemtRUjg5eV8tSzZGVkxqbVpSZDFGN3NGYXJILW1GZUlxQm9pYTJTMW42dnZpTi1BY01hSGdpVzhIQ3V8gIrAsqONx8YBgNmGzL5hOD51dqxNZBRWYo5TtoUtum0=";
        //        String cookies = getCookie();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", cookies);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch order: " + response.getStatusCode());
        }
    }

    @Override
    public String saveOrder(Long id) throws IOException, InterruptedException {
        String response = getOrderById(id);

        JsonNode root = objectMapper.readTree(response);
        JsonNode data = root.get("data");

        Order order = objectMapper.treeToValue(data, Order.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "anonymous";
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        }
        if (order.getStatus().size() == 0) {

            order.getStatus().add(Status.builder()
                    .updateBy(username)
                    .status("Đã tạo đơn hàng thành công")
                    .createdAt(LocalDateTime.now())
                    .build());
            orderRepository.save(order);
            return "Tạo đơn hàng thành công";

        }
        return "Đơn hàng được tạo";
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public String processOrder(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "anonymous";
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        }
        Optional<Order> orderProcess = orderRepository.findById(id + "");
        if (orderProcess.isPresent() && orderProcess.get().getStatus().size() == 1) {

            orderProcess.get().getStatus().add(Status.builder()
                    .updateBy(username)
                    .status("Đang xử lý")
                    .createdAt(LocalDateTime.now())
                    .build());
            orderRepository.save(orderProcess.get());
            return "Đã chuyển trạng thái đơn hàng sang đang xử lý";

        }
        return "Đơn hàng đã đang được xử lý";

    }

    @Override
    public List<Order> getOrderOfUser(String id) {
        if (id == null || id.trim().isEmpty()) {
            log.warn("Invalid id: {}", id);
            return Collections.emptyList();
        }
        User user = userRepository.findById(id).orElse(null); // Tìm theo _id
        if (user == null) {
            log.warn("User not found for id: {}", id);
            return Collections.emptyList();
        }
        log.warn("User name for id: {}", user.getUserName());

        List<Order> lst = orderRepository.findAll();
        List<Order> lstOrder = new ArrayList<>();
        for (Order order: lst) {
            if(order.getCreatedBy().equalsIgnoreCase(user.getUserName())){
                lstOrder.add(order);
            }
        }
        log.info("Found {} orders for id: {}", lstOrder.size(), id);
        return lst.isEmpty() ? Collections.emptyList() : lst;
    }

//    @Override
//    public List<Order> getOrderOfUser(Long id) {
//        Optional<User> user = userRepository.findById(id+"");
//        var lst = orderRepository.findAll().stream().filter(e -> e.getCreatedBy()
//                .equalsIgnoreCase(user.get().getUserName()))
//                .toList();
//        if(CollectionUtils.isEmpty(lst)){
//            return null;
//        }
//        return lst;
//    }

//    @Override
//    public String getCookie() {
//        try {
//            System.out.println(" Gọi khởi tạo login flow API...");
//
//            ResponseEntity<String> initResponse = restTemplate.getForEntity(
//                    "https://dev-account.geohub.vn/login",
//                    String.class
//            );
//
//            JsonNode initJson = objectMapper.readTree(initResponse.getBody());
//            String flowId = initJson.get("id").asText();
//            System.out.println("✅ Flow ID: " + flowId);
//
//            // Chuẩn bị payload đăng nhập
//            ObjectNode credentials = objectMapper.createObjectNode();
//            credentials.put("csrf_token", "password");
//            credentials.put("method", "password");
//            credentials.put("identifier", "thaond120501@gmail.com");
//            credentials.put("password", "Vega@123");
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            HttpEntity<String> loginRequest = new HttpEntity<>(
//                    objectMapper.writeValueAsString(credentials), headers
//            );
//
//            System.out.println(" Gửi request đăng nhập...");
//
//            ResponseEntity<String> loginResponse = restTemplate.exchange(
//                    "https://dev-account.geohub.vn/login" + flowId,
//                    HttpMethod.POST,
//                    loginRequest,
//                    String.class
//            );
//
//            String responseBody = loginResponse.getBody();
//            if (responseBody == null || !responseBody.contains("\"authenticated\":true")) {
//                System.err.println(" Đăng nhập thất bại: response body không có 'authenticated:true'");
//                System.err.println("🔍Body: " + responseBody);
//                return null;
//            }
//
//            List<String> cookies = loginResponse.getHeaders().get(HttpHeaders.SET_COOKIE);
//            if (cookies == null || cookies.isEmpty()) {
//                System.err.println(" Không có cookie nào trong phản hồi.");
//                return null;
//            }
//
//            String cookieHeader = cookies.stream()
//                    .map(c -> c.split(";")[0])
//                    .collect(Collectors.joining("; "));
//
//            System.out.println("Lấy cookie thành công: " + cookieHeader);
//            return cookieHeader;
//
//        } catch (Exception e) {
//            System.err.println(" Exception khi login: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }
}
