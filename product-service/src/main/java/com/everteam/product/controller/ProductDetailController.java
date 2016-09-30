package com.everteam.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.everteam.product.model.ProductDetail;
import com.everteam.product.model.ProductDetailRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
// @RequestMapping("/")
public class ProductDetailController {
    private final ProductDetailRepository repository;

    @Autowired
    public ProductDetailController(ProductDetailRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return "<a href=\"swagger-ui.html\">Open in Swagger UI";
    }

    @ApiOperation(value = "Get All Products")
    @RequestMapping(path = "/getAllProducts", method = RequestMethod.GET)
    public @ResponseBody Iterable findAll() {
        return repository.findAll();
    }

    @ApiOperation(value = "Lists Products with Pagination")
    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public Iterable findAll(@RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "count", defaultValue = "10", required = false) int count, @RequestParam(value = "order", defaultValue = "ASC", required = false) Sort.Direction direction, @RequestParam(value = "sort", defaultValue = "productName", required = false) String sortProperty) {
        Page result = repository.findAll(new PageRequest(page, count, new Sort(direction, sortProperty)));
        return result.getContent();
    }

    @ApiOperation(value = "Show Info of a Single Product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success", response = String.class), @ApiResponse(code = 401, message = "Unauthorized"),
                            @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 404, message = "Not Found"),
                            @ApiResponse(code = 500, message = "Failure") })
    @RequestMapping(path = "/product/{id}", method = RequestMethod.GET)
    public @ResponseBody ProductDetail getProduct(@PathVariable("id") String id) {
        ProductDetail prod = repository.findOne(id);
        return prod;
    }

    @ApiOperation(value = "Creates a new Product")
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ProductDetail create(@RequestBody ProductDetail detail) {
        return repository.save(detail);
    }

}
