package com.bit.microservices.mitra.http.impl;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.mitra.http.HttpService;
import com.bit.microservices.mitra.model.dto.city.CityAPIResponseDTO;
import com.bit.microservices.mitra.model.dto.country.CountryAPIResponseDTO;
import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIDTO;
import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HttpServiceImpl implements HttpService {

    @Autowired
    private WebClient.Builder webClient;
    @Override
    public Mono<List<CountryAPIResponseDTO>> getListCountry() {
        URI url = URI.create("https://restcountries.com/v3.1/all");

        Map<String, String> headerList = new HashMap<>();
        headerList.put("Accept", MediaType.ALL_VALUE);
        headerList.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return this.webClient.build()
                .get()
                .uri(url)
                .headers(header -> header.addAll(header))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CountryAPIResponseDTO>>() {
                })
                .doOnError(err -> {

                    try{
                        WebClientResponseException exp = (WebClientResponseException) err;
                        ExceptionPrinter print = new ExceptionPrinter(err);
                        log.error("Error Get List Country",print.getMessage());

                    }catch (Exception Err){
                        ExceptionPrinter printz = new ExceptionPrinter(err);
                        log.error("Error Call Country API : {}",printz.getMessage());

                    }
                });
    }

    @Override
    public Mono<List<CityAPIResponseDTO>> getListCityByCountryCode(String countryCode) {

        String requestUrl="https://api.countrystatecity.in/v1/countries/%s/cities";
        String requestUrlFormatted = requestUrl.formatted(countryCode);

        URI url = URI.create(requestUrlFormatted);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.ALL_VALUE);
        httpHeaders.set("X-CSCAPI-KEY","dkNFbFdjMUJ5REFiWG9UUE5CVE1SY3JORG1vSG5EOTdqblNXSjhHZA==");
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return this.webClient.build()
                .get()
                .uri(url)
                .headers(header -> header.addAll(httpHeaders))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CityAPIResponseDTO>>() {
                })
                .doOnError(err -> {

                    try{
                        WebClientResponseException exp = (WebClientResponseException) err;
                        ExceptionPrinter print = new ExceptionPrinter(err);
                        log.error("Error Get List City {}",print.getMessage());

                    }catch (Exception Err){
                        ExceptionPrinter printz = new ExceptionPrinter(err);
                        log.error("Error Call City API : {}",printz.getMessage());

                    }
                });
    }

    @Override
    public Mono<CurrencyAPIResponseDTO> getCurrencyList() {
        String requestUrl="https://openexchangerates.org/api/currencies.json";

        URI url = URI.create(requestUrl);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", MediaType.ALL_VALUE);
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        return this.webClient.build()
                .get()
                .uri(url)
                .headers(header -> header.addAll(httpHeaders))
                .retrieve()
                .bodyToMono(CurrencyAPIResponseDTO.class)
                .doOnError(err -> {

                    try{
                        WebClientResponseException exp = (WebClientResponseException) err;
                        ExceptionPrinter print = new ExceptionPrinter(err);
                        log.error("Error Get List Currency {}",print.getMessage());

                    }catch (Exception Err){
                        ExceptionPrinter printz = new ExceptionPrinter(err);
                        log.error("Error Call Currency API : {}",printz.getMessage());

                    }
                });
    }
}
