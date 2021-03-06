package com.bahlam.brms.ch3;

import com.bahlam.brms.training.model.Customer;
import com.bahlam.brms.training.model.Order;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.kie.scanner.KieModuleMetaData;

public class KieContainerMavenTest {
	
	@Test
    public void loadingRulesFromExistingArtifact() {
        System.out.println("### Running loadingRulesFromExistingArtifact() Test ###");
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.bahlam.brms.training", 
                                                                    "ch3-kjar-simple-discounts", 
                                                                    "1.0.0"));

        Results results = kContainer.verify();
        results.getMessages().stream().forEach((message) -> {
            System.out.println(">> Message ( "+message.getLevel()+" ): "+message.getText());
        });
        assertThat(false, is(results.hasMessages(Message.Level.ERROR)));
        kContainer.getKieBaseNames().stream().map((kieBase) -> {
            System.out.println(">> Loading KieBase: "+ kieBase );
            return kieBase;
        }).forEach((kieBase) -> {
            kContainer.getKieSessionNamesInKieBase(kieBase).stream().forEach((kieSession) -> {
                System.out.println("\t >> Containing KieSession: "+ kieSession );
            });
        });
        
        KieSession kieSession = kContainer.newKieSession("rules.simple.discount");
        
        KieModuleMetaData kieModuleMetaData = KieModuleMetaData.Factory.newKieModuleMetaData(
                                                        ks.newReleaseId("com.bahlam.brms.training", 
                                                                        "ch3-kjar-simple-discounts", 
                                                                        "1.0.0"));
        
        kieModuleMetaData.getPackages().stream().map((pkg) -> {
            System.out.println(" >> Package Loaded:  "+pkg);
            return pkg;
        }).forEach((pkg) -> {
            kieModuleMetaData.getRuleNamesInPackage(pkg).stream().forEach((rule) -> {
                System.out.println("\t >> Contain Rule:  "+rule);
            });
        });

        Customer customer = new Customer();
        customer.setCategory(Customer.Category.SILVER);

        Order order = new Order();
        order.setCustomer(customer);

        kieSession.insert(customer);
        kieSession.insert(order);

        int fired = kieSession.fireAllRules();

        assertThat(1, is(fired));
        assertThat(10.0, is(order.getDiscount().getPercentage()));
        
        System.out.println("### Finished loadingRulesFromExistingArtifact() Test ###");

    }


    @Test
    public void loadingRulesFromAnotherExistingArtifact() {
        System.out.println("### Running loadingRulesFromAnotherExistingArtifact() Test ###");
        KieServices ks = KieServices.Factory.get();

        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.bahlam.brms.training", 
                                                                "ch3-kjar-premium-discounts", 
                                                                "1.0.0"));

        Results results = kContainer.verify();
        results.getMessages().stream().forEach((message) -> {
            System.out.println(">> Message ( "+message.getLevel()+" ): "+message.getText());
        });
        assertThat(false, is(results.hasMessages(Message.Level.ERROR)));
        kContainer.getKieBaseNames().stream().map((kieBase) -> {
            System.out.println(">> Loading KieBase: "+ kieBase );
            return kieBase;
        }).forEach((kieBase) -> {
            kContainer.getKieSessionNamesInKieBase(kieBase).stream().forEach((kieSession) -> {
                System.out.println("\t >> Containing KieSession: "+ kieSession );
            });
        });

        KieSession kieSession = kContainer.newKieSession("rules.premium.discount");
        
        KieModuleMetaData kieModuleMetaData = KieModuleMetaData.Factory.newKieModuleMetaData(
                                                        ks.newReleaseId("com.bahlam.brms.training", 
                                                                "ch3-kjar-premium-discounts", 
                                                                "1.0.0"));
        
        kieModuleMetaData.getPackages().stream().map((pkg) -> {
            System.out.println(" >> Package Loaded:  "+pkg);
            return pkg;
        }).forEach((pkg) -> {
            kieModuleMetaData.getRuleNamesInPackage(pkg).stream().forEach((rule) -> {
                System.out.println("\t >> Contain Rule:  "+rule);
            });
        });

        Customer customer = new Customer();
        customer.setCategory(Customer.Category.GOLD);

        Order order = new Order();
        order.setCustomer(customer);

        kieSession.insert(customer);
        kieSession.insert(order);

        int fired = kieSession.fireAllRules();

        assertThat(1, is(fired));
        assertThat(20.0, is(order.getDiscount().getPercentage()));


        System.out.println("### Finished loadingRulesFromAnotherExistingArtifact() Test ###");

    }


    @Test
    public void loadingRulesFromParentExistingArtifact() {
        System.out.println("### Running loadingRulesFromParentExistingArtifact() Test ###");
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.newKieContainer(ks.newReleaseId("com.bahlam.brms.training", 
                                                                "ch3-kjar-parent", 
                                                                "1.0.0"));

        Results results = kContainer.verify();
        results.getMessages().stream().forEach((message) -> {
            System.out.println(">> Message ( "+message.getLevel()+" ): "+message.getText());
        });
        assertThat(false, is(results.hasMessages(Message.Level.ERROR)));
        
        kContainer.getKieBaseNames().stream().map((kieBase) -> {
            System.out.println(">> Loading KieBase: "+ kieBase );
            return kieBase;
        }).forEach((kieBase) -> {
            kContainer.getKieSessionNamesInKieBase(kieBase).stream().forEach((kieSession) -> {
                System.out.println("\t >> Containing KieSession: "+ kieSession );
            });
        });
        
        KieSession kieSession = kContainer.newKieSession("rules.discount.all");

        KieModuleMetaData kieModuleMetaData = KieModuleMetaData.Factory.newKieModuleMetaData(
                                                        ks.newReleaseId("com.bahlam.brms.training", 
                                                                "ch3-kjar-parent", 
                                                                "1.0.0"));
        
        kieModuleMetaData.getPackages().stream().map((pkg) -> {
            System.out.println(" >> Package Loaded:  "+pkg);
            return pkg;
        }).forEach((pkg) -> {
            kieModuleMetaData.getRuleNamesInPackage(pkg).stream().forEach((rule) -> {
                System.out.println("\t >> Contain Rule:  "+rule);
            });
        });
        
        Customer customerGold = new Customer();
        customerGold.setCustomerId(1L);
        customerGold.setCategory(Customer.Category.GOLD);

        Order orderGold = new Order();
        orderGold.setCustomer(customerGold);

        kieSession.insert(customerGold);
        kieSession.insert(orderGold);

        Customer customerSilver = new Customer();
        customerSilver.setCustomerId(2L);
        customerSilver.setCategory(Customer.Category.SILVER);

        Order orderSilver = new Order();
        orderSilver.setCustomer(customerSilver);

        kieSession.insert(customerSilver);
        kieSession.insert(orderSilver);

        int fired = kieSession.fireAllRules();

        assertThat(2, is(fired));
        assertThat(10.0, is(orderSilver.getDiscount().getPercentage()));
        assertThat(20.0, is(orderGold.getDiscount().getPercentage()));

        System.out.println("### Finished loadingRulesFromParentExistingArtifact() Test ###");
    }

}
