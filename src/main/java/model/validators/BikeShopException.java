package model.validators;

public class BikeShopException extends RuntimeException {

    public BikeShopException(String message) {

        super(message);
    }

    public BikeShopException(String message, Throwable cause) {

        super(message, cause);
    }

    public BikeShopException(Throwable cause) {

        super(cause);
    }
}
