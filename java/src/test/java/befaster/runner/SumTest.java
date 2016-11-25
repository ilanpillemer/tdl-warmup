package befaster.runner;

import befaster.solutions.Sum;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SumTest {

    @Test
    public void compute_sum() {
        assertThat(Sum.apply(1, 1), equalTo(2));
    }
}