package com.zenlinda.eisenhower.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zenlinda.eisenhower.web.rest.TestUtil;

public class DegreUrgenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DegreUrgence.class);
        DegreUrgence degreUrgence1 = new DegreUrgence();
        degreUrgence1.setId(1L);
        DegreUrgence degreUrgence2 = new DegreUrgence();
        degreUrgence2.setId(degreUrgence1.getId());
        assertThat(degreUrgence1).isEqualTo(degreUrgence2);
        degreUrgence2.setId(2L);
        assertThat(degreUrgence1).isNotEqualTo(degreUrgence2);
        degreUrgence1.setId(null);
        assertThat(degreUrgence1).isNotEqualTo(degreUrgence2);
    }
}
