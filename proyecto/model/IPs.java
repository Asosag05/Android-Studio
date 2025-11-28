// tu package

import com.google.gson.annotations.SerializedName;

//CODIGO PARA EL JSON
public class IPs {

    @SerializedName("success")
    private Boolean success;

    @SerializedName("ip")
    private String ip;

    @SerializedName("type")
    private String type;

    @SerializedName("continent_name")
    private String continent_name;

    @SerializedName("country_name")
    private String country_name;

    @SerializedName("city")
    private String city;

    @SerializedName("zip")
    private String zip;

    @SerializedName("error")
    private ErrorData error; // Solo con code

    // Clase interna para mapear el objeto error
    public static class ErrorData {
        @SerializedName("code")
        private int code;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public boolean isSuccess() {
        // Si success es null, lo asumimos true
        return success == null || success;
    }

    public int getCode() {
        if (error != null) {
            return error.getCode();
        }
        return 0;
    }

    // Getters y setters básicos
    public String getIp() {
        return ip;
    }

    public String getType() {
        return type;
    }

    public String getContinent_name() {
        return continent_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setContinent_name(String continent_name) {
        this.continent_name = continent_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setError(ErrorData error) {
        this.error = error;
    }
}
