package com.trolit.github.grocerystore.services.product;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductCommonMethods {
    public static Integer returnPercentageDiffBetweenPrices(BigDecimal currentPrice, BigDecimal previousPrice) {
        int result = 0;

        if (previousPrice.signum() <= 0) {
            return 0;
        }

        if (currentPrice.compareTo(previousPrice) > 0) {
            result = ((currentPrice.subtract(previousPrice))
                    .divide(previousPrice, 2, RoundingMode.HALF_UP))
                    .multiply(new BigDecimal(100))
                    .intValue();
        } else {
            result = ((previousPrice.subtract(currentPrice))
                    .divide(previousPrice, 2, RoundingMode.HALF_UP))
                    .multiply(new BigDecimal(100))
                    .intValue();
        }

        return result;
    }
}
