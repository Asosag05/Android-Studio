// tu package


import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class descargaIPThread implements Runnable {

    private final String urlString;
    private final DescargaListener listener;

    public descargaIPThread(String urlString, DescargaListener listener) { // clase constructora
        this.urlString = urlString;
        this.listener = listener;
    }

    @Override
    public void run() {
        HttpURLConnection connection = null; // metodo run, accede a internet, obtiene datos o devuelve errores
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // accede a la url y hace un get, si no devuelve respuesta en 5000ns hace un time out
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();

            InputStream stream;
            if (responseCode >= 200 && responseCode < 300) {
                stream = connection.getInputStream(); // en esta parte de codigo se diferencia si los errores son de tipo http o son de la propia api, con esto podemos devolver el error adecuado para cada situacion
            } else {
                stream = connection.getErrorStream();
            }

            if (stream != null) { // Se obtienen los datos de la url
                InputStreamReader reader = new InputStreamReader(stream);
                Gson gson = new Gson();
                IPs ipData = gson.fromJson(reader, IPs.class);
                reader.close();

                if (ipData != null && !ipData.isSuccess()) { // si en la url la ip no es valida, se devuelve con codigo de error de api
                    int codigoError = ipData.getCode();
                    String errorMessage = getErrorMessage(codigoError);
                    listener.onDescargaError(new Exception(codigoError + ", " + errorMessage));
                } else if (ipData != null) { // si es valida se obtienen los valores de la url
                    listener.onDescargaCompletada(java.util.Arrays.asList(ipData));
                } else {
                    listener.onDescargaError(new Exception("No se pudo parsear la respuesta")); // si algo falla devuelve error
                }
            } else {
                listener.onDescargaError(new Exception("Error de conexión: " + responseCode)); // linea para errores tipo http
            }

        } catch (Exception e) {
            listener.onDescargaError(e);
        } finally {
            if (connection != null) {
                connection.disconnect(); // sale de la url
            }
        }
    }

    private String getErrorMessage(int codigoError) { // si no ha funcionado y hay un error, devuelve de que tipo es el error
        switch (codigoError) {
            case 106: return "IP inválida";
            case 101: return "Parámetro de acceso inválido";
            case 102: return "Usuario inactivo";
            case 103: return "Límite de API excedido";
            case 104: return "Límite de uso mensual excedido";
            default: return "Error desconocido en la API";
        }
    }
    public interface DescargaListener { // interfaz usada
        void onDescargaCompletada(java.util.List<IPs> ipsList);
        void onDescargaError(Exception e);
    }
}
