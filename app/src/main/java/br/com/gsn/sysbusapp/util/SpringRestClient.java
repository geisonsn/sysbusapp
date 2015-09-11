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
import java.util.Arrays;

import br.com.gsn.sysbusapp.model.AbstractSpringRestResponse;
import br.com.gsn.sysbusapp.model.SpringRestResponse;

/**
 * Created by Geison on 23/08/2015.
 */
public final class SpringRestClient {

    /**
     * Atributo usado para indicar se a mensagem pós request deve ser exibida
     */
    private static boolean showMessage = true;

    public SpringRestClient showMessage(boolean showMessage) {
        this.showMessage = showMessage;
        return this;
    }

    public static <T> SpringRestResponse post(Context context, final String url, final T param, Class<T> returnType) {

        if (!isConnected(context)) {
            return new SpringRestResponse(context, AbstractSpringRestResponse.CONNECTION_FAILED, showMessage);
        }

        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(new MediaType("application", "json"));
            requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<T> requestEntity = new HttpEntity<>(param, requestHeaders);

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, returnType);

            return new SpringRestResponse(context, responseEntity.getBody(), responseEntity.getStatusCode().value(), showMessage);

        } catch (HttpStatusCodeException e) {
            return new SpringRestResponse(context, e.getStatusCode().value(), showMessage);
        } catch (RestClientException e) {
            return new SpringRestResponse(context, AbstractSpringRestResponse.CONNECTION_FAILED, showMessage);
        } catch (Exception e) {
            return new SpringRestResponse(context, AbstractSpringRestResponse.UNEXPECTED_ERROR, showMessage);
        }
    }

    public static <T> SpringRestResponse postForObject(Context context, final String url, final T param, Class<T> returnType)
            throws HttpStatusCodeException {

        if (!isConnected(context)) {
            return new SpringRestResponse(context, AbstractSpringRestResponse.CONNECTION_FAILED, showMessage);
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            T returnObject = restTemplate.postForObject(url, param, returnType);

            return new SpringRestResponse(context, returnObject, HttpURLConnection.HTTP_OK, showMessage);
        } catch (HttpStatusCodeException e) {
            return new SpringRestResponse(context, e.getStatusCode().value(), showMessage);
        } catch (RestClientException e) {
            return new SpringRestResponse(context, AbstractSpringRestResponse.CONNECTION_FAILED, showMessage);
        } catch (Exception e) {
            return new SpringRestResponse(context, AbstractSpringRestResponse.UNEXPECTED_ERROR, showMessage);
        }
    }

    public static <T> SpringRestResponse getForObject(Context context, final String url, Class<T> returnType) {

        if (!isConnected(context)) {
            return new SpringRestResponse(context, AbstractSpringRestResponse.CONNECTION_FAILED, showMessage);
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            T returnObject = restTemplate.getForObject(url, returnType);

            return new SpringRestResponse(context, returnObject, HttpURLConnection.HTTP_OK, showMessage);
        } catch (HttpStatusCodeException e) {
            return new SpringRestResponse(context, e.getStatusCode().value(), showMessage);
        } catch (RestClientException e) {
            return new SpringRestResponse(context, AbstractSpringRestResponse.CONNECTION_FAILED, showMessage);
        } catch (Exception e) {
            return new SpringRestResponse(context, AbstractSpringRestResponse.UNEXPECTED_ERROR, showMessage);
        }
    }

    private static boolean isConnected(Context context) {
        return ConnectionUtil.isOnline(context);
    }
}
