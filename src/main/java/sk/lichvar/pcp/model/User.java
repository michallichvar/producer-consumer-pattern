package sk.lichvar.pcp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name = "susers")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "user_guid")
	private String userGuid;

	@Column(name = "user_name")
	private String userName;

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("userId", userId)
				.append("userGuid", userGuid)
				.append("userName", userName)
				.toString();
	}
}
