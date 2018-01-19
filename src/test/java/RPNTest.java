import exception.IncorectArgumentExeption;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RPNTest {
    RPN rpn;

    @Before
    public void setUp() throws Exception {
        rpn = new RPN();
    }

    @Test
    public void createStringRPN() throws IncorectArgumentExeption {
        Assert.assertEquals("abc*+", rpn.createStringRPN("(a+(b*c))"));
        Assert.assertEquals("ab+zx+*", rpn.createStringRPN("((a+b)*(z+x))"));
        Assert.assertEquals("at+bac++cd+^*", rpn.createStringRPN("((a+t)*((b+(a+c))^(c+d)))"));
    }

    @Test
    public void isValidateExpression() {
        Assert.assertEquals(true, rpn.isValidateExpression("((a+t)*((b+(a+c))^(c+d)))"));
        Assert.assertEquals(false, rpn.isValidateExpression("(%a+t)"));
    }

    @Test(expected = IncorectArgumentExeption.class)
    public void isCorrectNumberOfExpression() throws IncorectArgumentExeption {
        rpn.isCorrectNumberOfExpression(111);
    }

    @Test(expected = IncorectArgumentExeption.class)
    public void isCorrectLengthOfExpression() throws IncorectArgumentExeption {
        rpn.isCorrectLengthOfExpression(401);
    }

    @Test
    public void isOperator() {
        Assert.assertEquals(true, rpn.isOperator('+'));
    }
}