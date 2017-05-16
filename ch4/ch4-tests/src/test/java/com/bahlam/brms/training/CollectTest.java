package com.bahlam.brms.training;

import org.junit.Test;
import org.kie.api.runtime.KieSession;

import com.bahlam.brms.training.model.Order;
import com.bahlam.brms.training.util.BaseTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class CollectTest extends BaseTest {
	protected final String ksessionName = "collectKsession";

    @Test
    public void testInsertModifyAndDelete() {
        KieSession ksession = createSession(ksessionName);
        Order order1 = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        ksession.insert(order1);
        ksession.insert(order2);
        ksession.insert(order3);
        int firedRules = ksession.fireAllRules();
        assertThat(1, equalTo(firedRules));
        
        ksession.insert(new Order());
        firedRules = ksession.fireAllRules();
        assertThat(1, equalTo(firedRules));
    }

}
