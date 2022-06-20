package contract.entities;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    
    private Long id;

    @NotNull(message = "категория не выбрана")
    private Category category;

    @NotNull(message = "не может быть пустым")
    @Pattern(regexp = "([0-9]{1,})", message = "недопустимый символ")
    @Size(min = 8, max = 8, message = "требуется 8 числовых символов")
    private String vendorCode;

    @NotNull(message = "не может быть пустым")
    @Size(min = 5, max = 250, message = "требуется минимум 5 символов")
    private String title;

    private String shortDescription;

    private String fullDescription;

    @NotNull(message = "не может быть пустым")
    @DecimalMin(value = "0.01", message = "минимальное значение 0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
}
