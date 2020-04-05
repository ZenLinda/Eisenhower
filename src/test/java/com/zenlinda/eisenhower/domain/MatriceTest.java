package com.zenlinda.eisenhower.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zenlinda.eisenhower.web.rest.TestUtil;

public class MatriceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Matrice.class);
        Matrice matrice1 = new Matrice();
        matrice1.setId(1L);
        Matrice matrice2 = new Matrice();
        matrice2.setId(matrice1.getId());
        assertThat(matrice1).isEqualTo(matrice2);
        matrice2.setId(2L);
        assertThat(matrice1).isNotEqualTo(matrice2);
        matrice1.setId(null);
        assertThat(matrice1).isNotEqualTo(matrice2);
    }
}
