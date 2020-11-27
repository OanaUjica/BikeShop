package model.validators;

import model.Sale;

import java.time.LocalDate;

public class SaleValidator implements Validator<Sale> {

    @Override
    public void validate(Sale sale) {

        String message = "";

        message += sale.getSaleDate().isAfter(LocalDate.now()) ? "The sale date cannot be after the current date." : "";

        if (!message.equals("")) {
            throw new BikeShopException(message);
        }
    }
}
