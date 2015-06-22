package no.nb.microservices.filters;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 
 * @author ronnymikalsen
 *
 */
@Component
public class SimpleCORSFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulResponseHeader("Access-Control-Allow-Origin", "*");
        ctx.addZuulResponseHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        ctx.addZuulResponseHeader("Access-Control-Max-Age", "3600");
        ctx.addZuulResponseHeader("Access-Control-Allow-Headers", "x-requested-with, Origin, Content-Type, Accept, amsso");
        ctx.addZuulResponseHeader("Access-Control-Allow-Credentials", "true");
        return null;
    }

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

}
