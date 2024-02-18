package hexlet.code.dto.urls;

import hexlet.code.model.BasePage;
import hexlet.code.model.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UrlPage extends BasePage {
    private Url url;
}
