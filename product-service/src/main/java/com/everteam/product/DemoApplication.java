package com.everteam.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.everteam.product.model.ProductDetail;
import com.everteam.product.model.ProductDetailRepository;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// @RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2

public class DemoApplication {

    // @RequestMapping("/")
    // String home() {
    // return "<b>Product Service Demo App<b>";
    // }

    public static void main(String[] args) {

        System.setProperty("spring.config.name", "product-server");

        ApplicationContext ctx = SpringApplication.run(DemoApplication.class);
        ProductDetailRepository repo = (ProductDetailRepository) ctx.getBean(ProductDetailRepository.class);

        System.out.println(repo.getClass());
        for (int i = 1; i <= 25; i++) {
            ProductDetail prod = new ProductDetail();
            prod.setProductId("A" + i);
            prod.setInventoryId("Inv_A" + i);
            prod.setProductName("ProductName of " + i);
            prod.setShortDescription("Short Desc of " + i);
            prod.setLongDescription("Long Desc of " + i);
            repo.save(prod);
        }

        for (ProductDetail productDetail : repo.findAll()) {
            System.out.println(productDetail.getProductId());
        }

    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("product-service").pathMapping("/product-service").apiInfo(apiInfo()).select().apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Product Service").description("Product Service Rest Services").version("1.0").build();
    }

}
