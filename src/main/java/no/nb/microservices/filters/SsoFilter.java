package no.nb.microservices.filters;

import javax.servlet.http.HttpServletRequest;

import no.nb.commons.web.util.UserUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        RequestContext ctx = RequestContext.getCurrentContext();
        
        ctx.addZuulRequestHeader(UserUtils.REAL_IP_HEADER, UserUtils.getClientIp(request));
    }

    private void addAmssoToRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

        RequestContext ctx = RequestContext.getCurrentContext();

        ctx.addZuulRequestHeader(UserUtils.SSO_HEADER, UserUtils.getSsoToken(request));
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
