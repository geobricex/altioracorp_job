package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.modelmapper.ModelMapper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Methods {


    /**
     * Convert from string to json.
     *
     * @param json String type variable, contains the json to be converted.
     * @return a json.
     */
    public static JsonObject stringToJSON(String json) {
        try {
            JsonParser parser = new JsonParser();
            JsonObject Jso = parser.parse(json).getAsJsonObject();
            return Jso;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new JsonObject();
        }
    }

    /**
     * From json to String
     *
     * @param jso     Variable type json, contains the information.
     * @param param   String type variable, contains the name of the json
     *                parameter to be divided.
     * @param defaulx String type variable, return variable
     * @return a String, with data loaded from the json.
     */
    public static String JsonToString(JsonObject jso, String param, String defaulx) {
        try {
            JsonElement res = securGetJSON(jso, param);
            if (res != null) {
                String result = res.getAsString();
                result = result.trim().replace("\n", "\\n").replace("\t", "\\t").replace("'", "''");
                return result;
            } else {
                return defaulx;
            }
        } catch (Exception e) {
//            System.out.println("erro json a string");
            return defaulx;
        }
    }

    /**
     * Get a part of the json.
     *
     * @param jso   Variable type json, contains the information.
     * @param param String type variable, contains the name of the json
     *              parameter to be divided.
     * @return a json, divided.
     */
    public static JsonElement securGetJSON(JsonObject jso, String param) {
        try {
            JsonElement res = jso.get(param);//request.getParameter(param);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * from json to Integer.
     *
     * @param jso     Variable type json, contains the information
     * @param param   String type variable, contains the name of the json
     *                parameter to be divided.
     * @param defaulx String type Integer, return variable
     * @return an integer, the variable is defaulx.
     */
    public static int JsonToInteger(JsonObject jso, String param, int defaulx) {
        try {
            JsonElement res = securGetJSON(jso, param);
            if (res != null) {
                return res.getAsInt();
            } else {
                return defaulx;
            }
        } catch (Exception e) {
            return defaulx;
        }
    }

    public static String getJsonMessage(String status, String information, String data) {
        return "{\"status\":" + status + ",\"information\":\"" + information + "\",\"data\":" + data + "}";
    }

    public static <T, D> D convertEntityToDto(T entity, Class<D> dtoClass) {
        ModelMapper modelMapper = new ModelMapper();
        D dto = modelMapper.map(entity, dtoClass);
        return dto;
    }

    public static Boolean verifyMaxLength(String text, int length) {
        return (text.length() <= length);
    }

    public static int wordsCount(String text, String spaces) {
        int contador = 1, pos;//**METODO PIRATEADO XD
        text = text.trim(); //eliminar los posibles espacios en blanco al principio y al final
        if (text.isEmpty()) { //si la cadena está vacía
            contador = 0;
        } else {
            pos = text.indexOf(spaces); //se busca el primer espacio en blanco
            while (pos != -1) {   //mientras que se encuentre un espacio en blanco
                contador++;    //se cuenta una palabra
                pos = text.indexOf(spaces, pos + spaces.length()); //se busca el siguiente espacio en blanco
            }                                    //a continuación del actual
        }
        return contador;
    }

    public static Boolean verifyString(String text, String unEqString, int length) {
        if (!text.equals(unEqString)) {
            // -1 para indicar que no existe un maxlength
            if (length > -1) {
                return (text.length() <= length);
            } else {
                return true;
            }
        }
        return false;
    }

    public static Boolean testregex(String pattern, String text) {
        Pattern pat = Pattern.compile(pattern);
        Matcher mat = pat.matcher(text);
        return mat.matches();
    }

    public static boolean isInteger(String number) {
        try {
            int num = Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
