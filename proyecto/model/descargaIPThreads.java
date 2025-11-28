//tu package

import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class descargaIPThread implements Runnable {

    private final String urlString;
    private final DescargaListener listener;

    public descargaIPThread(String urlString, DescargaListener listener) {
        this.urlString = urlString;
        this.listener = listener;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());

                Gson gson = new Gson();
                // Cambio importante: deserializar un solo objeto IPs, no una lista
                IPs ipData = gson.fromJson(reader, IPs.class);

                reader.close();

                if (ipData != null) {
                    // Enviamos un array con un solo elemento
                    listener.onDescargaCompletada(java.util.Arrays.asList(ipData));
                } else {
                    listener.onDescargaError(new Exception("No se pudo parsear la respuesta"));
                }
            } else {
                listener.onDescargaError(new Exception("Error HTTP: " + responseCode));
            }
        } catch (Exception e) {
            listener.onDescargaError(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public interface DescargaListener {
        void onDescargaCompletada(java.util.List<IPs> ipsList);
        void onDescargaError(Exception e);
    }
}

        }
    }
}
