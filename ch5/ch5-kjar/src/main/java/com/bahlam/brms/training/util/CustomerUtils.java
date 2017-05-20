package com.bahlam.brms.training.util;

import com.bahlam.brms.training.model.Customer;

public class CustomerUtils {
	
	public static String formatCustomer(Customer c) {
        return String.format(
                "[%s] %s", c.getCategory(), c.getName());
    }

}
