package mohammaddarwaish.com.github.userservice.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestEntityA {

    private Long id;

    private String fieldOne;

    private String fieldTwo;

    private TestEntityB testEntityB;

    private List<TestEntityB> testEntityBS;

}
