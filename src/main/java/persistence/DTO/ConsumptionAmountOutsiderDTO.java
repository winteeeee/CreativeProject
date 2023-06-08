package persistence.DTO;

import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ConsumptionAmountOutsiderDTO extends DTO implements Serializable {
    private int id;
    private int year;
    private int month;
    private String dong_code;
    private String dong_name;
    private String industry_code;
    private String industry_name;
    private String residential_city_code;
    private String residential_city_name;
    private double amount;

    public ConsumptionAmountOutsiderDTO(String[] line) throws IOException, CsvException {
        super(line);
    }
}
