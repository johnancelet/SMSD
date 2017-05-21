package app.util;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by winnerawan on 1/26/17.
 */
public class JsonUtil implements ResponseTransformer {

    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }

    public static String toJson(Object object) {

        return new Gson().toJson(object);

    }

    public static ResponseTransformer json() {

        return JsonUtil::toJson;
    }

    public static String JSON()
    {
        return "application/json";
    }
}
