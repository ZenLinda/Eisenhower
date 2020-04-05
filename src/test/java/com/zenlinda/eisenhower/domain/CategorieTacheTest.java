package com.zenlinda.eisenhower.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zenlinda.eisenhower.web.rest.TestUtil;

public class CategorieTacheTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieTache.class);
        CategorieTache categorieTache1 = new CategorieTache();
        categorieTache1.setId(1L);
        CategorieTache categorieTache2 = new CategorieTache();
        categorieTache2.setId(categorieTache1.getId());
        assertThat(categorieTache1).isEqualTo(categorieTache2);
        categorieTache2.setId(2L);
        assertThat(categorieTache1).isNotEqualTo(categorieTache2);
        categorieTache1.setId(null);
        assertThat(categorieTache1).isNotEqualTo(categorieTache2);
    }
}
