package br.com.gsn.sysbusapp.util;

import android.content.Context;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;

import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.SprintRestResponse;

/**
 * Created by Geison on 23/08/2015.
 */
public final class SpringRestClient {

    public static <T> SprintRestResponse post(Context context, final String url, final T param, Class<T> returnType) {
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(new MediaType("application", "json"));
            HttpEntity<T> requestEntity = new HttpEntity<>(param, requestHeaders);

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, returnType);

            return new SprintRestResponse(context, responseEntity.getBody(), responseEntity.getStatusCode().value());

        } catch (HttpStatusCodeException e) {
            return new SprintRestResponse(context, e.getStatusCode().value());
        } catch (RestClientException e) {
            return new SprintRestResponse(context, AbstractSpringRestResponse.CONNECTION_FAILED);
        } catch (Exception e) {
            return new SprintRestResponse(context, AbstractSpringRestResponse.UNEXPECTED_ERROR);
        }
    }

    public static <T> SprintRestResponse postForObject(Context context, final String url, final T param, Class<T> returnType)
            throws HttpStatusCodeException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            T returnObject = restTemplate.postForObject(url, param, returnType);

            return new SprintRestResponse(context, returnObject, HttpURLConnection.HTTP_OK);
        } catch (HttpStatusCodeException e) {
            return new SprintRestResponse(context, e.getStatusCode().value());
        } catch (RestClientException e) {
            return new SprintRestResponse(context, AbstractSpringRestResponse.CONNECTION_FAILED);
        } catch (Exception e) {
            return new SprintRestResponse(context, AbstractSpringRestResponse.UNEXPECTED_ERROR);
        }
    }

    public static <T> SprintRestResponse getForObject(Context context, final String url, Class<T> returnType) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            T returnObject = restTemplate.getForObject(url, returnType);

            return new SprintRestResponse(context, returnObject, HttpURLConnection.HTTP_OK);
        } catch (HttpStatusCodeException e) {
            return new SprintRestResponse(context, e.getStatusCode().value());
        } catch (RestClientException e) {
            return new SprintRestResponse(context, AbstractSpringRestResponse.CONNECTION_FAILED);
        } catch (Exception e) {
            return new SprintRestResponse(context, AbstractSpringRestResponse.UNEXPECTED_ERROR);
        }
    }
}
