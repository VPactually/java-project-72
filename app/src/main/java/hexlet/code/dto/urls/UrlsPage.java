package hexlet.code.dto.urls;

import hexlet.code.model.Url;
import hexlet.code.dto.BasePage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UrlsPage extends BasePage {
    private List<Url> urls;

    public UrlsPage(List<Url> urls) {
        this.urls = urls;
    }
}
