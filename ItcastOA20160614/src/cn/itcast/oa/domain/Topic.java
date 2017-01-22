package cn.itcast.oa.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
@Entity
@Table(name="itcast_topic")
public class Topic {
	//普通贴
	public static final int TYPE_NORMAL = 0;
	
	//精华帖
	public static final int TYPE_BEST = 1;
	
	//置顶帖
	public static final int TYPE_TOP = 2;
	
	private Long id;
	private String title;
	private String content;
	private User author;
	
	private Date postTime;
	private String ipAddr;
	
	private Forum forum;
	private Set<Reply> replies =new HashSet<Reply>();
	
	private int type;
	private Reply lastReply;
	private int replyCount;
	
	private Date lastUpdateTime;
	
	@ManyToOne
	@JoinColumn(name="authorId")
	public User getAuthor() {
		return author;
	}
	
	@Lob
	@Type(type="text")
	@Column(columnDefinition="TEXT",length = 16777215)
	public String getContent() {
		return content;
	}
	
	@ManyToOne
	@JoinColumn(name="forumId")
	public Forum getForum() {
		return forum;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public String getIpAddr() {
		return ipAddr;
	}
	
	@OneToOne
	@JoinColumn(name="lastReplyId")
	public Reply getLastReply() {
		return lastReply;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPostTime() {
		return postTime;
	}
	@OneToMany(mappedBy="topic",fetch=FetchType.EAGER)
	public Set<Reply> getReplies() {
		return replies;
	}

	public String getTitle() {
		return title;
	}

	public int getType() {
		return type;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public void setLastReply(Reply lastReply) {
		this.lastReply = lastReply;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public void setReplies(Set<Reply> replies) {
		this.replies = replies;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	
	
	
	
	
	
}
