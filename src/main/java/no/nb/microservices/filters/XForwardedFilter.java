package no.nb.microservices.filters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class XForwardedFilter extends ZuulFilter {
    public static final String X_FORWARDED_HOST = "X-Forwarded-Host";
    public static final String X_FORWARDED_PORT = "X-Forwarded-Port";
    public static final String X_FORWARDED_SSL = "X-Forwarded-Ssl";
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        
        ctx.addZuulResponseHeader(XForwardedFilter.X_FORWARDED_HOST, request.getHeader(XForwardedFilter.X_FORWARDED_HOST));
        ctx.addZuulResponseHeader(XForwardedFilter.X_FORWARDED_PORT, request.getHeader(XForwardedFilter.X_FORWARDED_PORT));
        ctx.addZuulResponseHeader(XForwardedFilter.X_FORWARDED_SSL, request.getHeader(XForwardedFilter.X_FORWARDED_SSL));
        ctx.addZuulResponseHeader(XForwardedFilter.X_FORWARDED_PROTO, request.getHeader(XForwardedFilter.X_FORWARDED_PROTO));
        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

}
