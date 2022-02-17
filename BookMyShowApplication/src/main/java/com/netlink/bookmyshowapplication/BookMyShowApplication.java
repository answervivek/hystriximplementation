package com.netlink.bookmyshowapplication;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableHystrix
@EnableHystrixDashboard
public class BookMyShowApplication {

    @Autowired
    private RestTemplate template;

    @HystrixCommand(fallbackMethod = "bookMyShowFallBack")
//    @HystrixCommand(fallbackMethod = "bookMyShowFallBack", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
//    })
    @GetMapping("/bookNow")
    public String bookShow() throws InterruptedException {
        Thread.sleep(2000);
//        String emailServiceResponse = template.getForObject("http://localhost:8181/emailService/send", String.class);
//        String paymentServiceResponse = template.getForObject("http://localhost:8282/paymentService/pay", String.class);
        System.out.println("Inside bookShow");
//        return emailServiceResponse + "\n" + paymentServiceResponse;
        return "Booking Successful";
    }

    @GetMapping("/bookNowWitoutHystrix")
    public String bookShowWitoutHystrix() {
        String emailServiceResponse = template.getForObject("http://localhost:8181/emailService/send", String.class);
        String paymentServiceResponse = template.getForObject("http://localhost:8282/paymentService/pay", String.class);
        return emailServiceResponse + "\n" + paymentServiceResponse;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookMyShowApplication.class, args);
    }

    public String bookMyShowFallBack() {
        return "Service of Netlink Gateway Failed";
    }

    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }
}
