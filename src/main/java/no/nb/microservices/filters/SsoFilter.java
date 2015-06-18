package no.nb.microservices.filters;

import no.nb.commons.web.util.UserUtils;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 
 * @author ronnymikalsen
 *
 */
@Component
public class SsoFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext.getCurrentContext().set("javaPreFilter-ran", true);

        addAmssoToRequest();

        addClientIpToRequest();

        return null;
    }

    private void addClientIpToRequest() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(UserUtils.REAL_IP_HEADER, UserUtils.getClientIp());
    }

    private void addAmssoToRequest() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(UserUtils.SSO_HEADER, UserUtils.getSsoToken());
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
