package tw.hyin.demo.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.NoArgsConstructor;

/**
 * @author H-yin on 2021.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
@Table(name = "UserInfo")
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PASS")
    private String userPass;
    
    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_AGE")
    private Integer age;

    @OneToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name="USER_ID")
    private Set<UserRole> userRoles;

}
