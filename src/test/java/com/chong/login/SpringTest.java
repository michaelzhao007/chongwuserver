package com.chong.login;


import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
   private ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
	//测试SessionFactory
	@Test
	public void testSessionFactory() throws Exception{
		SessionFactory sf =(SessionFactory)ac.getBean("sessionFactory");
		System.out.println("aa");
	    System.out.println(sf);
	}
    

}
