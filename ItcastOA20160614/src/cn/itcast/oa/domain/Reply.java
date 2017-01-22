package cn.itcast.oa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
@Entity
@Table(name="itcast_reply")
public class Reply {
	
	private Long id;
	private String title;
	private String content;
	private User author;
	
	private Date postTime;
	private String ipAddr;
	
	private Topic topic;
	
	@ManyToOne(fetch=FetchType.EAGER)
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

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public String getIpAddr() {
		return ipAddr;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getPostTime() {
		return postTime;
	}

	public String getTitle() {
		return title;
	}
	
	@ManyToOne
	@JoinColumn(name="topicId")
	public Topic getTopic() {
		return topic;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	
}
