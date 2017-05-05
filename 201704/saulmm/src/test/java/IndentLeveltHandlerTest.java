import domain.IndentLevelHandler;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class IndentLeveltHandlerTest {
    @Test public void thatIndentationIncrementsProperly() {
        IndentLevelHandler indentHandler = new IndentLevelHandler(0);

        indentHandler.sync("FOR");
        indentHandler.sync("IF");
        indentHandler.sync("FOR");
        indentHandler.sync("FOR");

        assertThat(indentHandler.indentation(), is(4));
    }

    @Test public void thatIndentationIncrementsDecrementsProperly() {
        IndentLevelHandler indentHandler = new IndentLevelHandler(0);

        indentHandler.sync("FOR");
        indentHandler.sync("IF");
        indentHandler.sync("NEXT");
        indentHandler.sync("ENDIF");

        assertThat(indentHandler.indentation(), is(0));
    }
}
