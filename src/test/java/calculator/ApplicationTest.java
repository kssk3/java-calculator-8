package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
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
        assertSimpleTest(() ->{
            run("//3\\n132343");
            assertThat(output().contains("결과 : 7"));
        });
    }

    // \\n, \n 둘다 개행 문자 개행 문자로 변경해서
    // "//3\\n132343", "//3\n132343" 입력값이 주어질 경우 둘다 테스트를 하고 싶었으나 하지 못했습니다.
    @Test
    void 커스텀_구분문자에_숫자사용_리터럴사용() {
        assertSimpleTest(() ->{
            assertThatThrownBy(() -> runException("//3\n132343"))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
