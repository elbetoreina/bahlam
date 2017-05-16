package com.bahlam.brms.training;

import org.junit.Test;
import org.kie.api.runtime.KieSession;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.bahlam.brms.training.model.Item;
import com.bahlam.brms.training.model.Order;
import com.bahlam.brms.training.model.OrderLine;
import com.bahlam.brms.training.util.BaseTest;

public class ConditionalElementsTest extends BaseTest {
	
protected final String ksessionName = "condElemsKsession";
    
    @Test
    public void testConditionalElements() {
        
        KieSession ksession = createSession(ksessionName);

        //first run: empty working memory
        int firedRules = ksession.fireAllRules();
        //warn about empty working memory rule should fire
        assertThat(firedRules, equalTo(1));

        //we add a lot of orders with at least one line
        Order order1 = createOrderOf("pencil", 1);
        Order order2 = createOrderOf("phone case", 4);
        Order order3 = createOrderOf("notebook", 3);
        Order order4 = createOrderOf("eraser", 34);
        Order order5 = createOrderOf("stickers", 2);
        
        //second run: many orders in the working memory
        ksession.insert(order1);
        ksession.insert(order2);
        ksession.insert(order3);
        ksession.insert(order4);
        ksession.insert(order5);
        firedRules = ksession.fireAllRules();
        //rule checking lines should fire once
        assertThat(firedRules, equalTo(1));
        
        Order order6 = new Order();
        //third run: one of the orders doesn't have any lines
        ksession.insert(order6);
        //no rule fires this time (not empty working memory, and not all elements have order lines)
        firedRules = ksession.fireAllRules();
        assertThat(firedRules, equalTo(0));
    }

    private Order createOrderOf(String itemName, int quantity) {
        Order order = new Order();
        List<OrderLine> orderLines = new ArrayList<OrderLine>();
        OrderLine line = new OrderLine();
        line.setItem(new Item(itemName, 1.0, 2.0));;
        line.setQuantity(quantity);
        orderLines.add(line);
        order.setItems(orderLines);
        return order;
    }

}
