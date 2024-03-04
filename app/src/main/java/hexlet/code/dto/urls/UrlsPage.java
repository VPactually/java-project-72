package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import hexlet.code.dto.BasePage;
import hexlet.code.model.UrlCheck;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UrlsPage extends BasePage {
    private List<Url> urls;
    private List<UrlCheck> checks;

    public UrlsPage(List<Url> urls) {
        this.urls = urls;
    }

}
