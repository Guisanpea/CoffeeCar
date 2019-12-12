package es.uma.ingweb.coffeecar.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopHierarchy {                             //result->records->infoBus
    @JsonProperty("result")
    private StopInfoResponse result;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StopInfoResponse {
        @JsonProperty("records")
        private List<StopData> stopsData;
        @JsonIgnoreProperties(ignoreUnknown = true)
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class StopData {
            private int _id;
            private int codParada;
            private double codLinea;
            private float lat;
            private float lon;
        }
    }

}
