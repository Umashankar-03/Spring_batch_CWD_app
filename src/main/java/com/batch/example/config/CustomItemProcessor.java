package com.batch.example.config;

import com.batch.example.model.Product;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Product, Product> {


    @Override
    public Product process(Product item) throws Exception {
        if (item == null) {
            throw new IllegalArgumentException("Product item cannot be null");
        }

        try {
            // Log the description (if needed for debugging)
            System.out.println("Processing product: " + item.getDescription());

            // Validate and parse price and discount

            String discountStr = item.getDiscount();
            String priceStr = item.getPrice();

            if (discountStr == null || priceStr == null || discountStr.trim().isEmpty() || priceStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Price or Discount cannot be null or empty");
            }

            int discountPer = Integer.parseInt(discountStr.trim());
            double originalPrice = Double.parseDouble(priceStr.trim());

            // Calculate discounted price
            double discount = (discountPer / 100.0) * originalPrice;
            double finalPrice = originalPrice - discount;

            // Set the discounted price
            item.setDiscountedPrice(String.format("%.2f", finalPrice)); // Formatting to 2 decimal places

        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format for price or discount in product: " + item, e);
        }

        return item;
    }
}
