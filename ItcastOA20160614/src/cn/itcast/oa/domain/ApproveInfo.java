package cn.itcast.oa.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 审批信息
 * 
 * @author tyg
 * 
 */
@Entity
@Table(name="itcast_approveInfo")
public class ApproveInfo {
	private Long id;
	private Application application; // 
	private User approver;// 审批人

	private Date approveTime;// 审批时间
	private boolean approval; // 是否通过
	private String comment; // 审批意见
	
	@ManyToOne
	public Application getApplication() {
		return application;
	}
	
	@ManyToOne
	public User getApprover() {
		return approver;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public String getComment() {
		return comment;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public void setApprover(User approver) {
		this.approver = approver;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
