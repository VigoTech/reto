import org.junit.Test;
import domain.util.StatementUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;


public class StatementUtilTest {
    @Test public void thatAnIndentedStatementIsProperlyCleaned() {
        String dirtyLine = "··»»»»»»ENDIF";

        String cleanedLine = StatementUtil.cleanLine(dirtyLine, '·', '»');

        assertThat(cleanedLine, is(equalTo("ENDIF")));
    }
}
