//tu package

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class descargaIPThread implements Runnable{
    private String urlServerService;
    private DescargaListener listener;

    public interface DescargaListener {
        void onDescargaCompletada(List<IPs> ipsList);
        void onDescargaError(Exception e);
    }

    public descargaIPThread(String urlServerService, DescargaListener listener) {
        this.urlServerService = urlServerService;
        this.listener = listener;
    }

    @Override
    public void run() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
        Gson gson = gsonBuilder.create();
        String response = null;

        try {
            response = NetUtils.getURLText(urlServerService);
            List<IPs> ipsList = Arrays.asList(gson.fromJson(response, IPs[].class));

            // Notificar éxito
            if (listener != null) {
                listener.onDescargaCompletada(ipsList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Notificar error
            if (listener != null) {
                listener.onDescargaError(e);
            }
        }
    }
}
