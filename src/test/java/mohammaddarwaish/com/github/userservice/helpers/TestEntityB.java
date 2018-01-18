package mohammaddarwaish.com.github.userservice.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestEntityB {

    private Long id;

    private String fieldOne;

    private String fieldTwo;

    private TestEntityA testEntityA;

}
