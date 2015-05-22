package no.nb.microservices.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * Created by andreasb on 13.03.15.
 */
@Component
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post"; // pre, routing, post
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        System.out.println("Myfilter is running! Got response body: " + RequestContext.getCurrentContext().getResponseBody());
        return "ok";
    }
}
