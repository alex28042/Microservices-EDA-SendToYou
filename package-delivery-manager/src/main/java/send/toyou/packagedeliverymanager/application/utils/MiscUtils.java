package send.toyou.packagedeliverymanager.application.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MiscUtils {

    @SneakyThrows
    public static String convertClassToMessage(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

        String json = objectWriter.writeValueAsString(object);
        return json.substring(1, json.length() - 1).replace("\n", "").replace("\r", "");
    }
}
