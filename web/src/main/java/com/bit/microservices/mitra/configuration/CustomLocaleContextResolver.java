package com.bit.microservices.mitra.configuration;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleTimeZoneAwareLocaleContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.util.Locale;
import java.util.TimeZone;

@Component(WebHttpHandlerBuilder.LOCALE_CONTEXT_RESOLVER_BEAN_NAME)
public class CustomLocaleContextResolver implements LocaleContextResolver{

	@Override
    public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {      
        return new SimpleTimeZoneAwareLocaleContext(Locale.getDefault(), TimeZone.getDefault());        
    }

    @Override
    public void setLocaleContext(ServerWebExchange exchange, LocaleContext localeContext) {
        throw new UnsupportedOperationException();
    }
}
