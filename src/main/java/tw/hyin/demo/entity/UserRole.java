/**
 * 
 */
package tw.hyin.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YingHan 2021-12-21
 *
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
@Table(name = "UserRole")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UR_NO")
    private String urNo;
	
    @Column(name = "USER_ID")
    private String userId;
	
	@Column(name = "ROLE_ID")
    private String roleId;
}
