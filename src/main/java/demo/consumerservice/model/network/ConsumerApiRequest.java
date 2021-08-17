package demo.consumerservice.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsumerApiRequest {

    private Long id;

    private String account;

    private String password;

    private String email;

    private String phoneNumber;

}
