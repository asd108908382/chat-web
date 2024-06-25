package org.sea.chat.domain.customer.gateway;

import org.sea.chat.domain.customer.Customer;

public interface CustomerGateway {
    Customer getByById(String customerId);
}
