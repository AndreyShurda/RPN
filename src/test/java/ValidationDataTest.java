import exception.IncorrectDataType;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationDataTest {
    @Test
    public void compile() {
        Assert.assertEquals(false,ValidationData.compile("(a%(b*c))"));
        Assert.assertEquals(true,ValidationData.compile("(a+(b*c))"));
        Assert.assertEquals(true,ValidationData.compile("((a+b)*(z+x))"));
        Assert.assertEquals(true,ValidationData.compile("((a+t)*((b+(a+c))^(c+d)))"));
    }

    @Test(expected = IncorrectDataType.class)
    public void check() throws IncorrectDataType {
       ValidationData.check("(a+(b*c)");
       ValidationData.check("((a+b)*(z%x))");
    }

    @Test
    public void isOperator() {
        Assert.assertEquals(true,ValidationData.isOperator('+'));
        Assert.assertEquals(true,ValidationData.isOperator('-'));
        Assert.assertEquals(true,ValidationData.isOperator('*'));
        Assert.assertEquals(true,ValidationData.isOperator('/'));
        Assert.assertEquals(true,ValidationData.isOperator('^'));
        Assert.assertEquals(true,ValidationData.isOperator(')'));
        Assert.assertEquals(true,ValidationData.isOperator('('));
        Assert.assertEquals(false,ValidationData.isOperator('%'));
        Assert.assertEquals(false,ValidationData.isOperator('a'));
    }
}