package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InitTest {

    @Test
    public void test() {
        var actual = 2 + 2;
        assertThat(actual).isEqualTo(4);
    }
}
