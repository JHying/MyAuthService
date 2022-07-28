package tw.hyin.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author H-yin on 2021.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert//解決 not null 欄位沒給值時，不會自動塞 default 的問題
@Table(name = "RoleInfo")
public class RoleInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "ROLE_ID")
    private String roleId;
	
	@Column(name = "ROLE_NAME")
    private String roleName;

}
