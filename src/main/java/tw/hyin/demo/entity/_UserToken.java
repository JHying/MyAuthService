package tw.hyin.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "UserToken")
@AllArgsConstructor
@NoArgsConstructor
public class _UserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TOKEN", nullable = false)
    private String token;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "EXPIRED_TIME")
    private Date expiredTime;

    @Column(name = "CREATE_TIME")
    private Date createTime;

}
