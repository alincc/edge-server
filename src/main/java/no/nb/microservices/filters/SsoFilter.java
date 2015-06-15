package no.nb.microservices.filters;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

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
        HttpServletRequest request = RequestContext.getCurrentContext()
                .getRequest();
        RequestContext ctx = RequestContext.getCurrentContext();
        
        String clientIp = getClientIp(request);
        validateClientIp(clientIp);

        ctx.addZuulRequestHeader("X-Original-IP-Fra-Frontend", "" + clientIp);
    }

    private void addAmssoToRequest() {
        HttpServletRequest request = RequestContext.getCurrentContext()
                .getRequest();

        RequestContext ctx = RequestContext.getCurrentContext();
        String amsso = getAmSso(request);
        ctx.addZuulRequestHeader("amsso", "" + amsso);
    }

    private String getAmSso(HttpServletRequest request) {
        
        String amsso = request.getHeader("amsso");
        
        if (amsso != null) {
            return amsso;
        } else {
            Cookie amssoCookie = WebUtils.getCookie(request, "amsso");
            if (amssoCookie != null) {
                return amssoCookie.getValue();
            }
        }
        
        return null;
    }

    private void validateClientIp(String clientIp) {
        if (!InetAddressValidator.getInstance().isValidInet4Address(clientIp)
                || "127.0.0.1".equals(clientIp)) {
            throw new SecurityException("localhost or IPv6 is not supported (" + clientIp + ")");
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Original-IP-Fra-Frontend");
        if (originalIpIsNull(clientIp)) {
            return request.getRemoteAddr();
        } else {
            return clientIp;
        }

    }

    private boolean originalIpIsNull(String clientIp) {
        return clientIp == null || clientIp.isEmpty();
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
