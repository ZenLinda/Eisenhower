package com.zenlinda.eisenhower.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zenlinda.eisenhower.web.rest.TestUtil;

public class DegreImportanceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DegreImportance.class);
        DegreImportance degreImportance1 = new DegreImportance();
        degreImportance1.setId(1L);
        DegreImportance degreImportance2 = new DegreImportance();
        degreImportance2.setId(degreImportance1.getId());
        assertThat(degreImportance1).isEqualTo(degreImportance2);
        degreImportance2.setId(2L);
        assertThat(degreImportance1).isNotEqualTo(degreImportance2);
        degreImportance1.setId(null);
        assertThat(degreImportance1).isNotEqualTo(degreImportance2);
    }
}
