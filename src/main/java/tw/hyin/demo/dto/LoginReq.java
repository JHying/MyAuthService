package tw.hyin.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author H-yin on 2020.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginReq implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @NotBlank
    private String userId;

    @NotBlank
    private String userPW;

}
