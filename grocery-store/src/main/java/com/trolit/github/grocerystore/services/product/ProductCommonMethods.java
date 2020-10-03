package com.trolit.github.grocerystore.services.product;

import com.trolit.github.grocerystore.enums.PriceStatusEnum;

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

    public static String getPriceStatus(BigDecimal price, BigDecimal previousPrice) {
        if (previousPrice == null || price.compareTo(previousPrice) == 0) {
            return PriceStatusEnum.unchanged.toString();
        } else if (price.subtract(previousPrice).signum() > 0) {
            return PriceStatusEnum.rise.toString();
        } else {
            return PriceStatusEnum.discount.toString();
        }
    }
}
