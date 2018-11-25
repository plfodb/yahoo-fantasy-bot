package com.pldfodb.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Instant start = Instant.now();

        try {
            traceRequest(request, body);
            ClientHttpResponse response = execution.execute(request, body);
            traceResponse(request, response, start);
            return response;
        } catch (SocketTimeoutException exception) {
            LOGGER.error("Request failed due to socket timeout.", exception);
            throw exception;
        } catch (Exception exception) {
            LOGGER.error("Request failed.", exception);
            throw exception;
        }
    }

    private synchronized void traceRequest(HttpRequest request, byte[] body) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("=========================== REQUEST ============================================");
            LOGGER.debug("URI         : {}", request.getURI());
            LOGGER.debug("Method      : {}", request.getMethod());
            LOGGER.debug("Headers     : {}", request.getHeaders());
            LOGGER.debug("Request size: {}", formatBytes(body.length));
            LOGGER.debug("Request body: {}", new String(body, "UTF-8"));
            LOGGER.debug("================================================================================");
        }
    }

    private synchronized void traceResponse(HttpRequest request, ClientHttpResponse response, Instant startTime) throws IOException {
        if (LOGGER.isDebugEnabled()) {
            StringBuilder inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }

            String responseBody = inputStringBuilder.toString();

            LOGGER.debug("============================ RESPONSE ==========================================");
            LOGGER.debug("Status code  : {}", response.getStatusCode());
            LOGGER.debug("Status text  : {}", response.getStatusText());
            LOGGER.debug("Headers      : {}", response.getHeaders());
            LOGGER.debug("Response size: {}", formatBytes(responseBody.getBytes().length));
            LOGGER.debug("Response body: {}", responseBody);
            LOGGER.debug("Response time: {}", elapsedTime(startTime.toEpochMilli(), System.currentTimeMillis()));
            LOGGER.debug("================================================================================");
        } else {
                LOGGER.info("Recieved {} response from {} {}.", response.getStatusCode(), request.getMethod(), request.getURI());
        }
    }

    private String elapsedTime(long startMillis, long endMillis) {
        long elapsed = startMillis > endMillis ? 0 : endMillis - startMillis;
        long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
        elapsed -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed);
        elapsed -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed);
        elapsed -= TimeUnit.SECONDS.toMillis(seconds);

        return String.format("%d:%02d:%02d.%03d", hours, minutes, seconds, elapsed);
    }

    private static String formatBytes(long bytes) {
        return StorageUnit.of(bytes).format(bytes);
    }

    private static enum StorageUnit {
        BYTE("bytes", 1L), KILOBYTE("KB", 1L << 10), MEGABYTE("MB", 1L << 20), GIGABYTE("GB", 1L << 30), TERABYTE("TB", 1L << 40), PETABYTE("PB", 1L << 50), EXABYTE("EB", 1L << 60);

        private StorageUnit(String name, long divisor) {
            symbol = name;
            this.divisor = divisor;
        }

        public static StorageUnit of(final long number) {
            final long n = number > 0 ? -number : number;
            if (n > -(1L << 10))
                return BYTE;
            else if (n > -(1L << 20))
                return KILOBYTE;
            else if (n > -(1L << 30))
                return MEGABYTE;
            else if (n > -(1L << 40))
                return GIGABYTE;
            else if (n > -(1L << 50))
                return TERABYTE;
            else if (n > -(1L << 60))
                return PETABYTE;
            else
                return EXABYTE;
        }

        public String format(long number) {
            return FMT.format((double) number / divisor) + " " + symbol;
        }

        private final String symbol;
        private final long divisor; // divider of BASE unit
        private static final NumberFormat FMT = NumberFormat.getInstance();

        static {
            FMT.setGroupingUsed(false);
            FMT.setMinimumFractionDigits(0);
            FMT.setMaximumFractionDigits(1);
        }
    }
}