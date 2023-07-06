package pojo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Product {
    private String title;
    private String description;
    private double price;
    private double discountPercentage;
    private double rating;
    private int stock;
    private String brand;
    private String category;
    private String thumbnail;
    private List<String> images;
}
