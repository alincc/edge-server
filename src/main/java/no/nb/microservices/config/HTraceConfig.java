package no.nb.microservices.config;

import javax.annotation.PostConstruct;

import org.apache.htrace.Trace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import no.nb.htrace.zuul.filters.HTraceZuulPostFilter;
import no.nb.htrace.zuul.filters.HTraceZuulPreFilter;

@Configuration
@Import(no.nb.htrace.config.HTraceConfig.class)
public class HTraceConfig {
 
    public String serviceName = "zuul";
    
    @PostConstruct
    public void init() {
        Trace.setProcessId("zuul");
    }
    
    @Bean
    public HTraceZuulPreFilter htracePreFilter() {
        return new HTraceZuulPreFilter();
    }

    @Bean
    public HTraceZuulPostFilter htracePostFilter() {
        return new HTraceZuulPostFilter();
    }

}
