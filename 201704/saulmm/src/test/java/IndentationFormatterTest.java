import domain.IndentLevelHandler;
import domain.IndentationFormatter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IndentationFormatterTest {
    @Test public void thatALineIsProperlyIndented() {
        IndentLevelHandler mockLevelHandler = mock(IndentLevelHandler.class);
        IndentationFormatter indentationFormatter = new IndentationFormatter("-", mockLevelHandler);

        when(mockLevelHandler.indentation()).thenReturn(5);
        String indented = indentationFormatter.indent("FOR yep");

        assertThat(indented, is(equalTo("-----FOR yep")));
    }
}
