package ru.dsluchenko.appointment.service.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SoapWebServiceConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/soapWS/*");
    }


    @Bean
    public XsdSchema doctorScheduleSchema() {
        return new SimpleXsdSchema(new ClassPathResource("doctorSchedule.xsd"));
    }

    @Bean("doctorSchedule")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema doctorScheduleSchema) {

        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();

        definition.setPortTypeName("DoctorSchedulePort");
        definition.setLocationUri("/soapWS");
        definition.setTargetNamespace("http://dsluchenko.ru/appointment/service/soap/domain");
        definition.setSchema(doctorScheduleSchema);
        return definition;
    }
}
