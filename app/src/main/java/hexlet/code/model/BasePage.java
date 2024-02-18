package hexlet.code.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasePage {
    private Object flash;
    private String flashInfo;

    public BasePage(Object flash, String flashInfo) {
        this.flash = flash;
        this.flashInfo = flashInfo;
    }
}
