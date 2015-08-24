package no.nb.microservices.config;

import javax.annotation.PostConstruct;

import org.apache.htrace.Trace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import no.nb.htrace.zuul.filters.HTraceCreateSamplePreFilter;
import no.nb.htrace.zuul.filters.HTraceZuulPostFilter;
import no.nb.htrace.zuul.filters.HTraceZuulPreFilter;
import no.nb.htrace.zuul.filters.HtraceAuthorizeSampleFilter;

@Configuration
@Import(no.nb.htrace.config.HTraceConfig.class)
public class HTraceConfig {
 
    private static final int SAMPLE_RATE = 100;
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

    @Bean
    public HTraceCreateSamplePreFilter createSamplePreFilter() {
        return new HTraceCreateSamplePreFilter(SAMPLE_RATE);
    }

    @Bean
    public HtraceAuthorizeSampleFilter htraceAuthorizeSampleFilter() {
        return new HtraceAuthorizeSampleFilter();
    }
}
