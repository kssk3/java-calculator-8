package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {

    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("-1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분문자에_숫자사용_백슬래쉬_2번사용() {
        assertSimpleTest(() -> {
            run("//3\\n132343");
            assertThat(output()).contains("결과 : 7");
        });
    }

    @Test
    void 커스텀_구분문자에_숫자사용_개행문자() {
        assertSimpleTest(() -> {
            assertThatThrownBy(() -> runException("//3\n132343"))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    void 연속된_구분자가_오는경우() {
        assertSimpleTest(() -> {
            run("1,,2,,3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 연속된_구분자_테스트() {
        assertSimpleTest(() -> {
            run("//..\\n1..2..3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
