package com.wendu.config.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {


    private final  String radio;
    /**
     * Records the remote address and will also set the session Id if a session already
     * exists (it won't create one).
     *
     * @param request that the authentication request was received from
     */
    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.radio = request.getParameter("radio");
    }


    public String getRadio() {
        return radio;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; radio: ").append(this.getRadio());
        return sb.toString();
    }
}
