package com.bahlam.brms.training;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;

import com.bahlam.brms.training.model.Coupon;
import com.bahlam.brms.training.model.Customer;
import com.bahlam.brms.training.model.Item;
import com.bahlam.brms.training.model.Order;
import com.bahlam.brms.training.model.OrderLine;
import com.bahlam.brms.training.types.IsGoldCustomer;
import com.bahlam.brms.training.types.IsLowRangeItem;
import com.bahlam.brms.training.util.BaseTest;

public class WorkingMemoryModificationTest extends BaseTest {
	
	protected final String ksessionName = "wmModificationKsession";
    
    @Test
    public void testInsertModifyAndDelete() {
        KieSession ksession = createSession(ksessionName);
        
        Customer customer = new Customer();
        customer.setName("John");
        Coupon coupon = new Coupon();
        coupon.setValidUntil(new Date(System.currentTimeMillis() - 3600)); // expired one hour ago
        Order order = new Order();
        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        for (int i = 0; i <= 10; i++) {
            orderLines.add(new OrderLine());
        }
        order.setItems(orderLines);
        order.setCustomer(customer);
        Item item = new Item("Cheap item", 9.00, 8.00);
        ksession.insert(customer);
        ksession.insert(coupon);
        ksession.insert(order);
        ksession.insert(item);
        int firedRUles = ksession.fireAllRules();
        assertThat(5, equalTo(firedRUles));

        //check it contains one object of type IsGoldCustomer
        Collection<?> goldCustomerObjs = ksession.getObjects(new ObjectFilter() {
            @Override
            public boolean accept(Object obj) {
                return obj instanceof IsGoldCustomer;
            }
        });
        assertThat(goldCustomerObjs, notNullValue());
        assertThat(1, equalTo(goldCustomerObjs.size()));
        IsGoldCustomer obj1 = (IsGoldCustomer) goldCustomerObjs.iterator().next();
        assertThat(obj1.getCustomer(), equalTo(customer));
        
      //check it contains one object of type IsLowRangeItem
        Collection<?> lowRangeItemObjs = ksession.getObjects(new ObjectFilter() {
            @Override
            public boolean accept(Object obj) {
                return obj instanceof IsLowRangeItem;
            }
        });
        assertThat(lowRangeItemObjs, notNullValue());
        assertThat(1, equalTo(lowRangeItemObjs.size()));
        IsLowRangeItem obj2 = (IsLowRangeItem) lowRangeItemObjs.iterator().next();
        assertThat(obj2.getItem(), equalTo(item));
    }
	

}
