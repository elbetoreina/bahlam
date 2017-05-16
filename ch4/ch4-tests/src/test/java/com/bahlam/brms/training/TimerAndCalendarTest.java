package com.bahlam.brms.training;

import com.bahlam.brms.training.model.Order;
import com.bahlam.brms.training.service.EmailService;
import com.bahlam.brms.training.util.BaseTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.time.Calendar;

public class TimerAndCalendarTest extends BaseTest {
	
	protected final String ksessionName = "timerCalendarKsession";
    
    @Test
    public void testInsertModifyAndDelete() {
        KieSession ksession = createSession(ksessionName);
        EmailService emailService = new EmailService();
        //set the global variable
        ksession.setGlobal("emailService", emailService );
        //inits the calendars
        ksession.getCalendars().set("weekends", new Calendar() {
            @Override
            public boolean isTimeIncluded(long timestamp) {
                return false;
            }
        });
        ksession.getCalendars().set("weekdays", new Calendar() {
            @Override
            public boolean isTimeIncluded(long timestamp) {
                return true;
            }
        });
        //configure a fake clock
        ksession.getSessionClock();
        Order order = new Order();
        order.setOrderId(1L);
        ksession.insert(order);
        int firedRules = ksession.fireAllRules();
        assertThat(1, equalTo(firedRules));
        assertThat(1, equalTo(emailService.getSentMessages().size()));
    }
	

}
